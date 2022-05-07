package br.com.kelvingcr.convidados.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.kelvingcr.convidados.R
import br.com.kelvingcr.convidados.service.model.GuestModel
import br.com.kelvingcr.convidados.view.listener.GuestListener
import br.com.kelvingcr.convidados.view.viewholder.GuestViewHolder

class GuestAdapter : RecyclerView.Adapter<GuestViewHolder>() {

    private var mGuestList: List<GuestModel> = arrayListOf()
    private lateinit var mListener: GuestListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        //Criando o layout
        val item = LayoutInflater.from(parent.context).inflate(R.layout.row_guest, parent, false)
        return GuestViewHolder(item, mListener)
    }

    /**
     * Pega os dados e atribui para o Layout
     */
    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        holder.bind(mGuestList[position])
    }

    override fun getItemCount() = mGuestList.count()

    fun updateGuests(list: List<GuestModel>) {
        mGuestList = list
        notifyDataSetChanged()
    }

    fun attachListener(listener: GuestListener) {
        mListener = listener
    }

}