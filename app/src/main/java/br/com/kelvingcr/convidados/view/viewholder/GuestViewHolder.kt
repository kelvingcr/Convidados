package br.com.kelvingcr.convidados.view.viewholder

import android.app.AlertDialog
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.kelvingcr.convidados.R
import br.com.kelvingcr.convidados.service.model.GuestModel
import br.com.kelvingcr.convidados.view.listener.GuestListener

class GuestViewHolder(itemView: View, private val listener: GuestListener) : RecyclerView.ViewHolder(itemView) {

    fun bind(guestModel: GuestModel) {
        //Pega o elemento do layout e atribui valor
       val textName = itemView.findViewById<TextView>(R.id.tvName)
        textName.text = guestModel.name

        textName.setOnClickListener{
            listener.onClick(guestModel.id)
        }
        textName.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle(R.string.remocao_convidado)
                .setMessage(R.string.deseja_remover)
                .setPositiveButton(R.string.remover) { dialog, which ->
                    listener.onDelete(guestModel.id)
                }
                .setNeutralButton(R.string.cancelar, null).show()
            true
        }
    }
}