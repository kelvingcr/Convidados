package br.com.kelvingcr.convidados.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.kelvingcr.convidados.databinding.FragmentPresentBinding
import br.com.kelvingcr.convidados.service.constants.GuestConstants
import br.com.kelvingcr.convidados.view.GuestFormActivity
import br.com.kelvingcr.convidados.view.adapter.GuestAdapter
import br.com.kelvingcr.convidados.view.listener.GuestListener
import br.com.kelvingcr.convidados.viewmodel.GuestsViewModel

class PresentFragment : Fragment() {

    private var _binding: FragmentPresentBinding? = null
    private lateinit var mViewModel: GuestsViewModel
    private lateinit var mListener: GuestListener
    private val mAdapter: GuestAdapter = GuestAdapter()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mViewModel = ViewModelProvider(this)[GuestsViewModel::class.java]
        _binding = FragmentPresentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //RecyclerView
        val recycler = binding.rvPresentGuests

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
                mViewModel.delete(id)
                mViewModel.load(GuestConstants.FILTER.PRESENT)
            }
        }
        mAdapter.attachListener(mListener)
        observer()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observer() {
        mViewModel.guestList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateGuests(it)
        })
    }

    override fun onResume() {
        super.onResume()
        mViewModel.load(GuestConstants.FILTER.PRESENT)
    }
}