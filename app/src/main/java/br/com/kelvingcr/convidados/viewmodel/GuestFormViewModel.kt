package br.com.kelvingcr.convidados.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.kelvingcr.convidados.service.model.GuestModel
import br.com.kelvingcr.convidados.service.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private val mGuestRepository: GuestRepository = GuestRepository.getInstance(mContext)

    private var mSaveGuest = MutableLiveData<Boolean>()
    val saveGuest: LiveData<Boolean> = mSaveGuest

    fun save(id: Int, name: String, presence: Boolean) {
        val guest = GuestModel(id, name, presence)
        if(id == 0 ) {
            mSaveGuest.value = mGuestRepository.save(guest)
        }else {
            mSaveGuest.value = mGuestRepository.update(guest)
        }
    }

    fun getAll() =  mGuestRepository.getAll()

    fun get(id: Int) =  mGuestRepository.get(id)

    fun update(guest: GuestModel) =  mGuestRepository.update(guest)

    fun delete(id: Int) =  mGuestRepository.delete(id)

    private var mGuest = MutableLiveData<GuestModel>()
    val guest: LiveData<GuestModel> = mGuest

    fun load(id: Int) {
        mGuest.value = mGuestRepository.get(id)
    }

}