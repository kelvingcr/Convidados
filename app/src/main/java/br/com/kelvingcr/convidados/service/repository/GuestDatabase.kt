package br.com.kelvingcr.convidados.service.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.kelvingcr.convidados.service.model.GuestModel


@Database(entities = arrayOf(GuestModel::class), version = 1)
abstract class GuestDatabase : RoomDatabase() {

    abstract fun guestDAO() : GuestDAO

    companion object {

        private lateinit var INSTANCE: GuestDatabase
        fun getDatabase(context: Context) : GuestDatabase {
            if (!::INSTANCE.isInitialized) {
                //Previnir que duas threads não utilizem ao mesmo tempo
                synchronized(GuestDatabase::class) {
                INSTANCE = Room.databaseBuilder(context, GuestDatabase::class.java, "guestDB")
                    .allowMainThreadQueries()
                    .build()
            }
        }
            return INSTANCE
        }
    }
}