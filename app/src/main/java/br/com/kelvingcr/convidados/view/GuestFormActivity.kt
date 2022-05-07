package br.com.kelvingcr.convidados.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.kelvingcr.convidados.viewmodel.GuestFormViewModel
import br.com.kelvingcr.convidados.R
import br.com.kelvingcr.convidados.databinding.ActivityGuestFormBinding
import br.com.kelvingcr.convidados.service.constants.GuestConstants

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var mViewModel: GuestFormViewModel
    private var mGuestId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this)[GuestFormViewModel::class.java]



 /*       mViewModel.getAll().filter { !it.presence }.forEach {
            println(it.id)
            println(it.name)
            println(it.presence)
        }


        println("Chamar ${mViewModel.get(3)?.name}")


        val guest = mViewModel.get(1)
        guest!!.name = "teste"

       mViewModel.update(guest)
        println("Chamar ${mViewModel.get(1)?.name}")

        //mViewModel.delete(1) */

        setListeners()
        observe()
        loadData()

        binding.rbPresence.isChecked = true
    }

    private fun loadData() {
        val bundle = intent.extras
        if(bundle != null) {
           mGuestId = bundle.getInt(GuestConstants.GUESTID)

            mViewModel.load(mGuestId)

            }
        }

    override fun onClick(view: View) {
        val id = view.id
        if(id == R.id.btnSave) {
            val name = binding.editName.text.toString()
            val presence = binding.rbPresence.isChecked

                mViewModel.save(mGuestId, name, presence)


        }
    }

    private fun observe() {
        mViewModel.saveGuest.observe(this, Observer {
            if(it) {
                Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(applicationContext, "Falha", Toast.LENGTH_SHORT).show()
            }
        })

        mViewModel.guest.observe(this, Observer {
            binding.editName.setText(it.name)
            if(it.presence) {
                binding.rbPresence.isChecked = true
            } else {
                binding.rbAbsent.isChecked = true
            }
        })

    }


    private fun setListeners() {
        binding.btnSave.setOnClickListener(this)
    }

}