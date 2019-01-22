package test.test.test.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 *  Anchor : Create by CimZzz
 *  Time : 2019/1/18 14:23:44
 *  Project : taoke_android
 *  Since Version : Alpha
 */
class DBHelper: SQLiteOpenHelper {
    constructor(context: Context) : super(
        context,
        "db_accessibility",
        null,
        1
    )


    fun checkExists(id: String): Boolean {
        val cursor = writableDatabase.rawQuery("select id from tb_subscribe where id='$id'", null)
        val exist = cursor.moveToNext()
        cursor.close()
        if(!exist)
            writableDatabase.execSQL("insert into tb_subscribe(id) values('$id')")
        return exist
    }


//    fun checkCount(): Long {
//        var count = -1L
//        val cursor = writableDatabase.rawQuery("select count(*) from tb_subscribe", null)
//        if(cursor.moveToNext())
//            count = cursor.getLong(0)
//        Log.v("CimZzz", count.toString())
//        cursor.close()
//        return count
//    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table tb_subscribe(id TEXT primary key)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
}