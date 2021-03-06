package com.tfxiaozi.smartfishtank.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CAMERA_RESOURCE".
*/
public class CameraResourceDao extends AbstractDao<CameraResource, Long> {

    public static final String TABLENAME = "CAMERA_RESOURCE";

    /**
     * Properties of entity CameraResource.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Sn = new Property(1, String.class, "sn", false, "sn");
        public final static Property ServerIp = new Property(2, String.class, "serverIp", false, "serverIp");
        public final static Property RtspAddr = new Property(3, String.class, "rtspAddr", false, "rtspAddr");
    }


    public CameraResourceDao(DaoConfig config) {
        super(config);
    }
    
    public CameraResourceDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CAMERA_RESOURCE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"sn\" TEXT," + // 1: sn
                "\"serverIp\" TEXT," + // 2: serverIp
                "\"rtspAddr\" TEXT);"); // 3: rtspAddr
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CAMERA_RESOURCE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CameraResource entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String sn = entity.getSn();
        if (sn != null) {
            stmt.bindString(2, sn);
        }
 
        String serverIp = entity.getServerIp();
        if (serverIp != null) {
            stmt.bindString(3, serverIp);
        }
 
        String rtspAddr = entity.getRtspAddr();
        if (rtspAddr != null) {
            stmt.bindString(4, rtspAddr);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CameraResource entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String sn = entity.getSn();
        if (sn != null) {
            stmt.bindString(2, sn);
        }
 
        String serverIp = entity.getServerIp();
        if (serverIp != null) {
            stmt.bindString(3, serverIp);
        }
 
        String rtspAddr = entity.getRtspAddr();
        if (rtspAddr != null) {
            stmt.bindString(4, rtspAddr);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CameraResource readEntity(Cursor cursor, int offset) {
        CameraResource entity = new CameraResource( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // sn
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // serverIp
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // rtspAddr
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CameraResource entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSn(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setServerIp(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setRtspAddr(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CameraResource entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CameraResource entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CameraResource entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
