package br.com.kelvingcr.convidados.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.kelvingcr.convidados.databinding.FragmentAllBinding
import br.com.kelvingcr.convidados.service.constants.GuestConstants
import br.com.kelvingcr.convidados.view.GuestFormActivity
import br.com.kelvingcr.convidados.view.adapter.GuestAdapter
import br.com.kelvingcr.convidados.view.listener.GuestListener
import br.com.kelvingcr.convidados.viewmodel.GuestsViewModel

class AllGuestsFragment : Fragment() {

    private var _binding: FragmentAllBinding? = null

    private lateinit var allGuestViewModel: GuestsViewModel
    private val mAdapter: GuestAdapter = GuestAdapter()
    private lateinit var mListener: GuestListener

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        allGuestViewModel = ViewModelProvider(this)[GuestsViewModel::class.java]

        _binding = FragmentAllBinding.inflate(inflater, container, false)

        val root: View = binding.root

        //RecyclerView
        val recycler = binding.rvAllGuests

        //Definir layout
        recycler.layoutManager = LinearLayoutManager(context)

        //Adapter
        recycler.adapter = mAdapter

        mListener = object : GuestListener {
            override fun onClick(id: Int) {

                val intent = Intent(context, GuestFormActivity::class.java)
                val bundle = Bundle()

                bundle.putInt(GuestConstants.GUESTID, id)
                intent.putExtras(bundle)

                startActivity(intent)

            }

            override fun onDelete(id: Int) {
                allGuestViewModel.delete(id)
                allGuestViewModel.load()
            }
        }
        mAdapter.attachListener(mListener)
        observer()

        return root
    }

    private fun observer() {
        allGuestViewModel.guestList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateGuests(it)
        })
    }

    override fun onResume() {
        super.onResume()
        allGuestViewModel.load()
    }

}