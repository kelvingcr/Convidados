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

class GuestRepository (context: Context) {

    private val mDatabase = GuestDatabase.getDatabase(context).guestDAO()

    fun getAll(): List<GuestModel> {
        return mDatabase.getInvited()
    }


    fun get(id: Int): GuestModel {
        return mDatabase.get(id)
    }

    @SuppressLint("Range")
    fun getPresent(): List<GuestModel> {
        return mDatabase.getPresent()
    }

    @SuppressLint("Range")
    fun getAbsent(): List<GuestModel> {
        return mDatabase.getAbsent()
    }

    fun save(obj: GuestModel): Boolean {
        return mDatabase.save(obj) > 0

    }

    fun update(obj: GuestModel): Boolean {
        return mDatabase.update(obj) > 0

    }

    fun delete(guest: GuestModel) {
        mDatabase.delete(guest)
    }

}