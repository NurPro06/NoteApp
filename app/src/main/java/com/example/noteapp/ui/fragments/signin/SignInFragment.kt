package com.example.noteapp.ui.fragments.signin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentSignInBinding
import com.example.noteapp.utlis.PreferenceHelper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth


class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var auth: FirebaseAuth
    private val preferenceHelper = PreferenceHelper()
    private lateinit var googleSignInClient: GoogleSignInClient
    private val signInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account.idToken)
                } catch (e: ApiException){
                    updateUI(null)
                }

            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(requireContext())
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.wed_id_client))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferenceHelper.init(requireContext())
        checkRegistrationStatus()
        setupListeners()
    }



    private fun setupListeners() {
        binding.tvSign.setOnClickListener {
            val signInIntent= googleSignInClient.signInIntent
            signInLauncher.launch(signInIntent)
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null )
        auth.signInWithCredential(credential)
            .addOnCompleteListener ( requireActivity()){ task ->
                if (task.isSuccessful){
                    val user = auth.currentUser
                    preferenceHelper.setRegistered(true)
                    updateUI(user)
                }else{
                    updateUI(null)
                }
                }


    }
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            findNavController().navigate(R.id.noteFragment)
        }else {
            Toast.makeText(requireContext(), "аутенфикация не удалось", Toast.LENGTH_SHORT).show()
        }
    }
    private fun checkRegistrationStatus() {
        if (preferenceHelper.isRegistered()){
            findNavController().navigate(R.id.noteFragment)
        }
    }
}