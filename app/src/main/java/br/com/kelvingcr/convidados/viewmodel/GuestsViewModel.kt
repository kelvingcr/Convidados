package br.com.kelvingcr.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.kelvingcr.convidados.service.constants.GuestConstants
import br.com.kelvingcr.convidados.service.model.GuestModel
import br.com.kelvingcr.convidados.service.repository.GuestRepository

class GuestsViewModel(application: Application) : AndroidViewModel(application) {

    private val mGuestRepository = GuestRepository.getInstance(application.applicationContext)

    private val mGuestList = MutableLiveData<List<GuestModel>>()

    val guestList: LiveData<List<GuestModel>> = mGuestList

    fun load(filter: Int = 0) {
        if (filter == GuestConstants.FILTER.EMPTY) {
            mGuestList.value = mGuestRepository.getAll()
        } else if (filter == GuestConstants.FILTER.PRESENT) {
            mGuestList.value = mGuestRepository.getPresent()
        } else if (filter == GuestConstants.FILTER.ABSENT) {
            mGuestList.value = mGuestRepository.getAbsent()
        }
    }

    fun delete(id: Int) = mGuestRepository.delete(id)
}