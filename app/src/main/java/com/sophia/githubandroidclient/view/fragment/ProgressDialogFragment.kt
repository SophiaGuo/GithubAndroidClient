package com.sophia.githubandroidclient.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.sophia.githubandroidclient.R
import com.sophia.githubandroidclient.databinding.FragmentProgressDialogBinding

/**
 *  @author: SophiaGuo
 */
class ProgressDialogFragment : DialogFragment() {

    private var messageResId: Int? = null

    companion object {
        fun newInstance() =
            ProgressDialogFragment()
    }

    private val binding: FragmentProgressDialogBinding by lazy {
        FragmentProgressDialogBinding.bind(requireView())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_progress_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textTips.text = getString(messageResId ?: R.string.loading)
    }

    fun show(
        fragmentManager: FragmentManager,
        @StringRes messageResId: Int,
        isCancelable: Boolean = false
    ) {
        this.messageResId = messageResId
        this.isCancelable = isCancelable
        try {
            show(fragmentManager, "progressDialogFragment")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}