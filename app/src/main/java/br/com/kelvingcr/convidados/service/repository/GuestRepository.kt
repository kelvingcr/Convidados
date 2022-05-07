package br.com.kelvingcr.convidados.service.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import androidx.core.content.contentValuesOf
import br.com.kelvingcr.convidados.service.constants.DataBaseConstants
import br.com.kelvingcr.convidados.service.model.GuestModel
import java.lang.Exception
import java.sql.DatabaseMetaData
import java.util.function.BinaryOperator

class GuestRepository private constructor(context: Context) {

    private var mGuestDataBaseHelper: GuestDataBaseHelper = GuestDataBaseHelper(context)

    // Singleton
    companion object {

        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if (!::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    @SuppressLint("Range")
    fun getAll(): List<GuestModel> {

        val list: MutableList<GuestModel> = ArrayList()
        return try {
            val db = mGuestDataBaseHelper.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )
            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )

            if(cursor != null && cursor.count > 0) {

                while (cursor.moveToNext()) {

                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence =
                        (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                    list.add(GuestModel(id, name, presence))
                }

            }
            cursor.close()
            list
        } catch (e: Exception) {
            list
        }
    }

    @SuppressLint("Range")
    fun getAllQuery(): List<GuestModel> {

        val list: MutableList<GuestModel> = ArrayList()
        return try {

            val db = mGuestDataBaseHelper.readableDatabase
            val cursor =  db.rawQuery("select * from Guest", null)

            if(cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence =
                        (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                    list.add(GuestModel(id, name, presence))
                }
            }
            cursor.close()
            list
        } catch (e: Exception) {
            list
        }
    }

    @SuppressLint("Range")
    fun get(id: Int): GuestModel? {

        var guest: GuestModel? = null

        return try {
            val db = mGuestDataBaseHelper.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                null
            )

            if(cursor != null && cursor.count > 0) {
                val moveToFirst = cursor.moveToFirst()

                val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                val presence = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                guest = GuestModel(id, name, presence)

            }
            cursor.close()
            guest
        } catch (e: Exception) {
            guest
        }
    }

    @SuppressLint("Range")
    fun getPresent(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()
        return try {

            val db = mGuestDataBaseHelper.readableDatabase
            val cursor =  db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 1", null)

            if(cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence =
                        (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                    list.add(GuestModel(id, name, presence))
                }
            }
            cursor.close()
            list
        } catch (e: Exception) {
            list
        }
    }

    @SuppressLint("Range")
    fun getAbsent(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()
        return try {

            val db = mGuestDataBaseHelper.readableDatabase
            val cursor =  db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 0", null)

            if(cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence =
                        (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                    list.add(GuestModel(id, name, presence))
                }
            }
            cursor.close()
            list
        } catch (e: Exception) {
            list
        }
    }

    fun save(obj: GuestModel): Boolean {

        return try {
            val db = mGuestDataBaseHelper.writableDatabase

            val insertValues = ContentValues()
            insertValues.put(DataBaseConstants.GUEST.COLUMNS.NAME, obj.name)
            insertValues.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, obj.presence)

            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, insertValues)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun update(obj: GuestModel): Boolean {

        return try {
            val db = mGuestDataBaseHelper.writableDatabase

            val insertValues = ContentValues()
            insertValues.put(DataBaseConstants.GUEST.COLUMNS.NAME, obj.name)
            insertValues.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, obj.presence)

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(obj.id.toString())

            db.update(DataBaseConstants.GUEST.TABLE_NAME, insertValues, selection, args)
            true
        } catch (e: Exception) {
            false
        }

    }

    fun delete(id: Int): Boolean {

        return try {
            val db = mGuestDataBaseHelper.writableDatabase

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)
            true
        } catch (e: Exception) {
            false
        }


    }

}