package com.db;

import com.db.Callbacks.*;
import com.db.Classes.*;
import java.sql.*;
import java.sql.Connection;
import java.util.concurrent.CompletableFuture;

public class ConnectionDB {
    private final String HOST;
    private final String DATABASE;
    private final String USER;
    private final String PASSWORD;
    private final String PORT;
    private Connection connection;


    public ConnectionDB(String HOST , String PORT , String USER, String PASSWORD , String DATABASE) {
        this.HOST = HOST;
        this.DATABASE = DATABASE;
        this.USER = USER;
        this.PASSWORD = PASSWORD;
        this.PORT = PORT;
        this.connection = null;
    }

    public ConnectionDB(Connection connection) {
        this.HOST = null;
        this.DATABASE = null;
        this.USER = null;
        this.PASSWORD = null;
        this.PORT = null;
        this.connection = connection;
    }

    public void connect(ConnectionCallback callback) {
        try {
            if(this.connection == null || this.connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String URL = "jdbc:mysql://" + this.HOST + ":" + this.PORT + "/" + this.DATABASE;
                this.connection = DriverManager.getConnection(URL, this.USER, this.PASSWORD);
                callback.call(null);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            callback.call(ex);
        }
    }

    public void disconnect(ConnectionCallback callback) {
        try {
            if(this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
                if(callback != null) {
                    callback.call(null);
                }
            }
        } catch (SQLException ex ) {
            if(callback != null) {
                callback.call(ex);
            }
        }
    }

    public void disconnect() {
        this.disconnect(null);
    }

    public void query(String SQL_QUERY , QueryCallback callback , Object[] params) {
        try {
            PreparedStatement stmt = this.connection.prepareStatement(SQL_QUERY);
            for(int i = 0 ; i < params.length ; i++){
                stmt.setObject(i+1,params[i]);
            }
            ResultSet result = stmt.executeQuery();
            ResultSetMetaData metaData = result.getMetaData();
            int columnCount = metaData.getColumnCount();
            QueryResults rows = new QueryResults();
            while(result.next()){
                QueryResult row = new QueryResult();
                for(int i = 1 ; i <= columnCount ; i++){
                    row.put(metaData.getColumnLabel(i) , result.getObject(i));
                }
                rows.add(row);
            }
            callback.call(null,rows);
        } catch (Exception e) {
            callback.call(e , null);
        }
    }

    public void query(String SQL_QUERY , QueryCallback callback) {
        query(SQL_QUERY , callback , new Object[]{});
    }

    // Async version of this

    public CompletableFuture<QueryResults> queryAsync(String SQL_QUERY , Object[] params) {
        CompletableFuture<QueryResults> future = new CompletableFuture<>();

        query(SQL_QUERY , (error, result) -> {
            if(error == null ) {
                future.complete(result);
            } else {
                future.completeExceptionally(error);
            }
        } , params);

        return future;
    };

    public CompletableFuture<QueryResults> queryAsync(String SQL_QUERY ) {
        return queryAsync(SQL_QUERY,new Object[]{});
    };

    // Sync version of this

    public QueryResults querySync(String SQL_QUERY , Object[] params) {
        try {
            return queryAsync(SQL_QUERY,params).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public QueryResults querySync(String SQL_QUERY) {
        return querySync(SQL_QUERY , new Object[]{});
    }


    public void firstQuery(String SQL_QUERY , firstQueryCallback callback , Object[] params) {
        query(SQL_QUERY , (error, results) -> {
            if(error != null) {
                callback.call(error,null);
            } else {
                if(results.isEmpty()) {
                    callback.call(null,new QueryResult());
                } else {
                    callback.call(null, results.getFirst());
                }
            }
        } , params);
    }

    public void firstQuery(String SQL_QUERY , firstQueryCallback callback ) {
        firstQuery(SQL_QUERY , callback , new Object[]{});
    }

    // Async version of this code

    public CompletableFuture<QueryResult> firstQueryAsync ( String SQL_QUERY , Object[] params ) {
        CompletableFuture<QueryResult> future = new CompletableFuture<>();

        firstQuery(SQL_QUERY , (error, results) -> {
            if(error == null) {
                future.complete(results);
            } else {
                future.completeExceptionally(error);
            }
        } , params);

        return future;
    }

    public CompletableFuture<QueryResult> firstQueryAsync ( String SQL_QUERY ) {
        return firstQueryAsync( SQL_QUERY , new Object[]{} );
    }

    // Sync version of this code

    public QueryResult firstQuerySync(String SQL_QUERY , Object[] params) {
        try {
            return firstQueryAsync(SQL_QUERY,params).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public QueryResult firstQuerySync(String SQL_QUERY) {
        return firstQuerySync(SQL_QUERY, new Object[]{});
    }

    public void execute(String SQL_EXECUTE , ExecuteCallback callback , Object[] params) {
        try {
            PreparedStatement stmt = this.connection.prepareStatement(SQL_EXECUTE);
            for(int i = 0 ; i < params.length ; i++){
                stmt.setObject(i+1,params[i]);
            }
            int affectedRows = stmt.executeUpdate();
            if(callback != null) callback.call(null , affectedRows);
        } catch (Exception e) {
            if(callback != null) callback.call(e,0);
        }
    }

    public void execute(String SQL_EXECUTE , ExecuteCallback callback ) {
        execute(SQL_EXECUTE,callback,new Object[]{});
    }

    // Async Version of this

    public CompletableFuture<Integer> executeAsync(String SQL_EXECUTE , Object[] params) {
        CompletableFuture<Integer> future = new CompletableFuture<>();

        execute(SQL_EXECUTE,(error, affectedRows) -> {
            if(error == null) {
                future.complete(affectedRows);
            } else {
                future.completeExceptionally(error);
            }
        },params);

        return future;
    }

    public CompletableFuture<Integer> executeAsync(String SQL_EXECUTE) {
        return executeAsync(SQL_EXECUTE,new Object[]{});
    }

    // Sync version of this

    public Integer executeSync(String SQL_EXECUTE , Object[] params) {
        try {
            return executeAsync(SQL_EXECUTE,params).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Integer executeSync(String SQL_EXECUTE) {
        return executeSync(SQL_EXECUTE,new Object[]{});
    }

    public Connection getConnection(){
        return  this.connection;
    }
}
