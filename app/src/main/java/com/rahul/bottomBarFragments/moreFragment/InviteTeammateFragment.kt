package com.rahul.bottomBarFragments.moreFragment

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.rahul.postRequest.Invitation
import com.rahul.salescrm.R
import com.rahul.salescrm.databinding.FragmentInviteTeammateBinding
import com.rahul.signUpAndLogInFragments.SignUpFragmentDirections
import com.rahul.utils.Resource
import com.rahul.utils.toast
import com.rahul.viewModel.moreVM.InviteTeammateViewModel


class InviteTeammateFragment : Fragment() {

    private lateinit var binding: FragmentInviteTeammateBinding
    private lateinit var navController: NavController
    private val viewModel: InviteTeammateViewModel by viewModels()
    var checkedName = "Admin"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentInviteTeammateBinding.inflate(inflater,container, false)
        binding.viewModelInXml = viewModel
        navController = findNavController()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.adminButton.isChecked = true

        (activity as AppCompatActivity).supportActionBar?.title = "Invite Teammate"


        viewModel.email.observe(viewLifecycleOwner, Observer {
            if (!Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
                binding.emailLayoutField.helperText = "Enter your valid email id"
                return@Observer
            }
            if (it.isNullOrEmpty()) {
                binding.emailLayoutField.helperText = "Field is required"
                return@Observer
            }
            viewModel.isEmailValid = true
            binding.emailLayoutField.helperText = null

            Log.i("email",viewModel.email.value.toString())
        })


        viewModel.invite.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    viewModel.progressBar(false)
                    viewModel.mainViewVisibility(true)
                    response.data?.let { message ->
                        Log.i("ServerResponse", message)
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                    val action = InviteTeammateFragmentDirections.actionInviteTeammateFragmentToTeammateListFragment()
                    navController.navigate(action)
                }

                is Resource.Error -> {
                    viewModel.progressBar(false)
                    viewModel.mainViewVisibility(true)
                    response.error?.let { error ->
                        Log.i("ServerResponse", error)
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()

                    }
                }

                is Resource.Loading -> {
                    viewModel.progressBar(true)
                    viewModel.mainViewVisibility(false)
                    Log.i("ServerResponse", "Loading")

                }
            }
        })

        binding.inviteButton.setOnClickListener {

            when(binding.radioGroup.checkedRadioButtonId){

                R.id.admin_button ->{
                    toast("Admin")
                    checkedName = "Admin"
//                    Log.i("Role",checkedName)
                }
                R.id.team_lead_button ->{
                    toast("team lead")
                    checkedName = "Team Lead"
//                    Log.i("Role",checkedName)
                }
                R.id.sales_button ->{
                    toast("sales")
                    checkedName = "Sales"
//                    Log.i("Role",checkedName)
                }
            }

            if (viewModel.isEmailValid){
                val invitation = Invitation(viewModel.email.value!!,checkedName)
                Log.i("Role",invitation.toString())
            }
        }



//             supportActionBar?.title = "hai"
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }


}