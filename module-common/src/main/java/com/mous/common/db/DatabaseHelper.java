package com.mous.common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ZN on 2015/12/3.
 * <p>
 * 数据库
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String SESSION_TABLE = "SESSION_TABLE"; // 最近一条记录
    public static final String CHAT_INFO_TABLE = "CHAT_INFO_TABLE"; // 聊天记录
    public static final String REMARK_NAME_TABLE = "REMARK_NAME_TABLE";//群备注的表
    private static final String DATA_BASE_NAME = "mous-im.db";
    private static final int DATA_BASE_VERSION = 1;


    private static final String LastChatRecordTable = "CREATE TABLE if not exists  [" + SESSION_TABLE + "](" +
            "[messageID] [varchar](20) primary key NOT NULL," +
            "[title] [varchar](20) NOT NULL," +
            "[lastContent] [varchar](20) NOT NULL," +
            "[talkingID] [varchar](20) NOT NULL," +
            "[senderID] [varchar] (20) NOT NULL," +
            "[mySelfID] [varchar](20) NOT NULL," +
            "[iconRes] [nvarchar](50)," +
            "[unReadCount] [integer] NOT NULL," +
            "[isGroup] [integer] NOT NULL," +
            "[groupID] [varchar](20)," +
            "[groupName] [varchar](20)," +
            "[time] [varchar](20) NOT NULL); ";


    private static final String ChatMessageRecordTable = "CREATE TABLE if not exists [" + CHAT_INFO_TABLE + "](" +
            "[messageID] [varchar] (20) primary key NOT NULL ," +
            "[chatInfoID] [varchar] (20) NOT NULL ," +
            "[iconRes] [varchar] (20)," +
            "[content] [nvarchar] ," +
            "[isGroup] [integer] NOT NULL ," +
            "[talkingID] [varchar](20) NOT NULL ," +
            "[senderID] [varchar](20) NOT NULL ," +
            "[senderName] [varchar](20) NOT NULL ," +
            "[mySelfID] [varchar](20) NOT NULL ," +
            "[infoType] [integer] NOT NULL ," +
            "[fileName] [nvarchar](50)," +
            "[filePath] [varchar](200)," +
            "[fileLocalPath] [varchar](200) ," +
            "[isMySend] [integer] NOT NULL ," +
            "[groupID] [varchar](20) ," +
            "[groupName] [varchar](20)," +
            "[time] [varchar](20) NOT NULL );";

    private static final String UserRemarkNameTable = "CREATE TABLE if not exists [" + REMARK_NAME_TABLE + "](" +
            "[MessageID] [varchar](50) primary key NOT NULL," +
            "[GroupID] [varchar](20) NOT NULL," +
            "[UserID] [varchar](20) NOT NULL," +
            "[RemarkName] [nvarchar] (50) NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("drop table if exists LastChatTable");
        db.execSQL("drop table if exists ChatInfoTable");
        db.execSQL("drop table if exists UserRemarkName");

        db.execSQL(LastChatRecordTable);
        db.execSQL(ChatMessageRecordTable);
        db.execSQL(UserRemarkNameTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
        }
    }
}