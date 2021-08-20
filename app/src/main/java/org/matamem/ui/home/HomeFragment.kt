package org.matamem.ui.home

import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import org.matamem.MainActivity
import org.matamem.WebViewSettings
import org.matamem.databinding.DlgSaveGroupBinding
import org.matamem.databinding.FragmentHomeBinding
import org.matamem.modals.Note
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var vm: HomeViewModel
    private lateinit var b : FragmentHomeBinding

    @Inject lateinit var webViewSettings : WebViewSettings

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vm = ViewModelProvider(this).get(HomeViewModel::class.java)
        b = FragmentHomeBinding.inflate(inflater, container, false)


        webViewSettings.setSettings(b.webView)


        b.pullToRefresh.setOnRefreshListener {
            b.webView.loadUrl(vm.currentLink.value!!)
        }

       // v.handleOnLinkChange("https://motamem.org/")
        b.webView.webViewClient = object : WebViewClient() {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(
                webView: WebView,
                request: WebResourceRequest
            ): Boolean {
                vm.handleOnLinkChange(request.url.toString())
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                b.pullToRefresh.isRefreshing = false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                b.pullToRefresh.isRefreshing = true
            }
        }


        vm.currentLink.observe(viewLifecycleOwner, Observer {
            b.webView.loadUrl(it)
        })

        vm.readMode.observe(viewLifecycleOwner, {
            if (it) {
                (activity as MainActivity).navBottomNavigation.visibility = View.GONE
                hideNotificationBar()
            } else {
                (activity as MainActivity).navBottomNavigation.visibility = View.VISIBLE
                showNotificationBar();
            }
        })

        vm.onBackPressed.observe(viewLifecycleOwner){
            if(it){
                activity?.finish()
            }
        }

        vm.backStackCount.observe(viewLifecycleOwner ){
            if(it == 0){
                b.lytBackStack.visibility = View.GONE
            }else{
                b.txtBackStack.text = it.toString()
                b.lytBackStack.visibility = View.VISIBLE
            }
        }

        b.lytBackStack.setOnClickListener{
            onBackPressed()
        }

        handleWebViewGesture()



        clipboardListener()
        return b.root
    }

    fun onBackPressed(){
        vm.onBackPressed()
    }

    private fun handleWebViewGesture(){
        var gd = GestureDetector(activity, object : SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent?): Boolean {
                vm.doubleClick()
                return super.onDoubleTap(e)
            }
        })

        b.webView.setOnTouchListener { v, event ->
            gd.onTouchEvent(event)
            false
        }
    }

    private fun hideNotificationBar(){
        @Suppress("DEPRECATION")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = activity?.window!!.insetsController

            if(controller != null) {
                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            activity?.window!!.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

    }

    private fun showNotificationBar(){
        @Suppress("DEPRECATION")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = activity?.window!!.insetsController

            if(controller != null) {
                controller.show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            activity?.window!!.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }

    }

    fun Group.setAllOnClickListener(listener: View.OnClickListener?) {
        referencedIds.forEach { id ->
            rootView.findViewById<View>(id).setOnClickListener(listener)
        }
    }


    private fun showSaveInGroupDlg(){
        var dlg = BottomSheetDialog(requireActivity())
        dlg.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        var dlgB = DlgSaveGroupBinding.inflate(LayoutInflater.from(context))
        dlg.setContentView( dlgB.root )

        Objects.requireNonNull<Window>(dlg.window).setBackgroundDrawable(
            ColorDrawable(
                Color.TRANSPARENT
            )
        )


        dlgB.rclItems.layoutManager = LinearLayoutManager(context)

        vm.groupList.observe(viewLifecycleOwner) {
            dlgB.rclItems.adapter = DlgGroupsAdapter(it.reversed()).setOnItemClickListener { it ->
                var text = Note(vm.text)
                text.group_id = it.id
                vm.addTextToGroup(text)
                Toast.makeText(context , "به دسته بندی افزوده شد!"  , Toast.LENGTH_SHORT).show();
            }
        }

        dlgB.btnAddGroup.setOnClickListener{
            vm.addGroup(dlgB.edtGroup.text.toString())
            dlgB.edtGroup.setText("")
        }


        dlg.show()
    }

    private fun clipboardListener(){

        b.imgCopy.setOnClickListener{
            showSaveInGroupDlg()
        }

        vm.copyImgVisible.observe(viewLifecycleOwner){
            if(it){
                b.imgCopy.visibility = View.VISIBLE
            }else{
                b.imgCopy.visibility = View.GONE
            }
        }

        val clipboard: ClipboardManager =
            activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.addPrimaryClipChangedListener {
            val a = clipboard.text.toString()
            vm.onCopyText(a)
        }

    }

}