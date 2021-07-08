package com.dmortal.artgallery

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dmortal.artgallery.databinding.FragmentGalleryBinding
import com.dmortal.artgallery.db.DBManager
import com.dmortal.artgallery.ds.MainData
import com.dmortal.artgallery.ds.Settings
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.fixedRateTimer
import kotlin.math.ceil


class GalleryFragment : Fragment() {

    private val TAG = this.javaClass.simpleName
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private var dbManager: DBManager? = null
    private var communicator: FragmentCommunicator? = null
    private var mainData: MainData? = null
    private var settings: Settings? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        communicator = activity as FragmentCommunicator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
/*        with(binding) {
            imageView.setOnClickListener {
                if (imageView.scaleType == ImageView.ScaleType.FIT_XY)
                    imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                else
                    imageView.scaleType = ImageView.ScaleType.FIT_XY
            }
        }*/
        initGalleryFragment(savedInstanceState)
        initAdapter()
        initRecyclerView()
        onScrollListener()
        onToolbarClickListener()
        return binding.root
    }

    override fun onStop() {
        super.onStop()
        dbManager?.saveSettings(settings)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun onToolbarClickListener() {
/*        binding.galleryToolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.item_shuffle) {
                settings?.let {
                    val newSettings = Settings()
                    val totalPages = it.totalPages

                    newSettings.firstPage = (1..totalPages).random()
//                    Log.e("${TAG} toolbar", newSettings.firstPage.toString())
                    it.deepCopy(newSettings)
                    communicator?.openGalleryFragment()
                }
            }
            true
        }*/
    }

    private fun onScrollListener() {
        with(binding.recyclerGalleryFragment) {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0) {
                        settings?.let {
                            val glManager = layoutManager as GridLayoutManager

                            it.firstPosition =
                                glManager.findFirstCompletelyVisibleItemPosition()
                            it.lastPosition =
                                glManager.findLastCompletelyVisibleItemPosition()

                            if (glManager.itemCount != it.previousItemCount)
                                it.loadingStatus = 0
                            if (it.loadingStatus == 0 &&
                                glManager.findLastVisibleItemPosition() >= glManager.itemCount - 1) {

/*
                                val size = mainData?.dataAdapter?.galleryData?.size ?: 1
                                it.currentPage = (
                                        ceil(size / (it.loadLimit + it.initLimit).toDouble()).toInt())
*/
/*
                                it.currentPage = (
                                        ceil(glManager.findLastVisibleItemPosition() / it.loadLimit.toDouble()).toInt())
*/
                                it.currentPage = (it.firstPage +
                                        ceil(imagesCount / it.loadLimit.toDouble()).toInt())

                                Log.e(TAG, "lastVisible = ${glManager.findLastVisibleItemPosition()}")
                                Log.e(TAG, "itemCount = ${imagesCount}")


                                it.previousItemCount = glManager.itemCount
                                it.loadingStatus = 1
                                loadMoreDataAsync(it.currentPage)
                            }
                        }
                    }
                }
            })
        }
    }

    private fun initGalleryFragment(savedInstanceState: Bundle?) {
        mainData = communicator?.getMainData()
        dbManager = context?.let { DBManager(it) }
        settings = dbManager?.getSettings()
//        settings?.toString()?.let { Log.e("${TAG} initGallery", it) }
//        mainData?.dataAdapter?.galleryData?.let { Log.e("${TAG} initGallery", it.size.toString()) }

    }

    fun initAdapter() {
        communicator?.let {
            communicator ->
            if (communicator.isFirstCreate()) {
                communicator.setIsFirstCreate(false)
                mainData?.let { data ->
                    settings?.let { settings ->
                        val call = data.service?.getImagesFromPage(
                            settings.firstPage,
                            settings.initLimit * settings.gridSpan,
                            settings.fields
                        )
                        loadDataAsync(call)
                    }
                }
            }
        }

    }

    private fun initRecyclerView() {
        with(binding.recyclerGalleryFragment) {
            mainData?.let {
                    mainData ->
                settings?.let {
                        settings ->
                    apply {
                        layoutManager = GridLayoutManager(context, settings.gridSpan)
                        adapter = mainData.dataAdapter
                    }
                    if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                        postDelayed({ scrollToPosition(settings.lastPosition) }, 200)
                    else
                        postDelayed({ scrollToPosition(settings.firstPosition) }, 200)
                }
            }
        }
    }

    fun loadDataAsync(call: Call<ResponseDTO>?) {
        call?.enqueue(object : Callback<ResponseDTO> {

            override fun onResponse(
                call: Call<ResponseDTO>,
                response: Response<ResponseDTO>
            ) {
                mainData?.dataAdapter?.galleryData.let {
                        galleryData ->

                    val withImageCollection = response.body()?.data?.filter { it -> !it.imageId.isNullOrBlank() }
                    Log.e(TAG, "START_ASYNC")
                    response.body()?.data?.forEach {
                        Log.e(TAG, "id = ${it.id}, imageId = ${it.imageId}")
                    }
                    Log.e(TAG, "END_ASYNC")
                    response.body()?.data?.let {
                        if (withImageCollection != null) {
                            galleryData?.addAll(withImageCollection)
                        }
                    }
//                    response.body()?.data?.let { galleryData?.addAll(it) }
                    imagesCount += response.body()?.data?.size ?: 0
//                    settings?.totalPages = response.body()?.pagination?.totatPages ?: 10
                    mainData?.dataAdapter?.submitList(galleryData)
                    mainData?.dataAdapter?.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {
                Log.e(TAG, t.printStackTrace().toString())
            }

        })
    }

    fun loadMoreDataAsync(page: Int) {
        mainData?.let {
                data ->
            settings?.let {
                    settings ->
                val call = data.service?.getImagesFromPage(
                    page, settings.loadLimit, settings.fields)
                Log.e(TAG, "page = ${page}")
                loadDataAsync(call)
            }
        }
    }

    companion object {

        private var imagesCount: Int = 0

        @JvmStatic
        fun newInstance() =
            GalleryFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}