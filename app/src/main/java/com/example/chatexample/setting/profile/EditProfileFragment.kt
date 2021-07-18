package com.example.chatexample.setting.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.chatexample.R
import com.example.chatexample.databinding.FragmentEditProfileBinding
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment(), EditProfileListener, ValueEventListener {
    private lateinit var editProfileBinding: FragmentEditProfileBinding
    private val viewModel : EditProfileViewModel by viewModels()
    private var reference: Query? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editProfileBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireActivity()),
            R.layout.fragment_edit_profile,
            container,
            false
        )
        return editProfileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editProfileBinding.listener = this
        reference = FirebaseDatabase.getInstance().getReference("user")
        reference?.addValueEventListener(this)
    }

    override fun showCropImage() {
        context?.apply {
            CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setFixAspectRatio(true)
                .setAspectRatio(1, 1)
                .setScaleType(CropImageView.ScaleType.CENTER)
                .start(this, this@EditProfileFragment)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                viewModel.avatar.postValue(result?.uri.toString())
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(context, "Cropping failed: " + result?.error, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onCancelled(error: DatabaseError) {
        TODO("Not yet implemented")
    }

    override fun onDataChange(snapshot: DataSnapshot) {

    }
}