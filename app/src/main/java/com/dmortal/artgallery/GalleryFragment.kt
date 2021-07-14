package com.dmortal.artgallery

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dmortal.artgallery.databinding.FragmentGalleryBinding
import com.dmortal.artgallery.viewmodel.DataViewModel
import com.dmortal.artgallery.viewmodel.InstanceSettingsViewModel
import com.dmortal.artgallery.viewmodel.PersistentSettingsViewModel
import java.lang.Thread.sleep
import kotlin.concurrent.thread
import kotlin.math.ceil

class GalleryFragment : Fragment() {

    private val TAG = this.javaClass.simpleName
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private var communicator: MainCommunicator? = null
    private var dataViewModel: DataViewModel? = null
    private var instanceSettingsViewModel: InstanceSettingsViewModel? = null
    private var persistentSettingsViewModel: PersistentSettingsViewModel? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        communicator = activity as MainCommunicator
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
        toolbarSetup()
        initViewModels()
        initAdapter()
        initRecyclerView()
        onScrollListener()
        onToolbarClickListener()
        setObservers()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun toolbarSetup() {
        communicator?.disableToolbarTitle()
    }

    private fun initViewModels() {
        dataViewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        instanceSettingsViewModel = ViewModelProvider(requireActivity()).get(InstanceSettingsViewModel::class.java)
        persistentSettingsViewModel = ViewModelProvider(requireActivity()).get(PersistentSettingsViewModel::class.java)
    }

    private fun initAdapter() {
        dataViewModel?.let { data ->
            instanceSettingsViewModel?.let { instanceSettings ->
                if (instanceSettings.isFirstCreate) {
                    instanceSettings.isFirstCreate = false
                    persistentSettingsViewModel?.let { persistentSettings ->
                        data.loadData(
                            instanceSettings.firstPage,
                            instanceSettings.initLimit * persistentSettings.getGridSpan(),
                            instanceSettings.fields
                        )
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        with(binding.recyclerGalleryFragment) {
            dataViewModel?.dataAdapter?.let { dataAdapter ->
                instanceSettingsViewModel?.let { instanceSettings ->
                    persistentSettingsViewModel?.let { persistentSettings ->
                        apply {
                            layoutManager = GridLayoutManager(
                                context,
                                persistentSettings.getGridSpan()
                            )
                            adapter = dataAdapter
//                                persistentSettings.getPersistentSettings()
//                                    ?.observe(viewLifecycleOwner, {
//                                        layoutManager = GridLayoutManager(
//                                            context,
//                                            persistentSettings.getGridSpan()
//                                        )
//                                        adapter = dataAdapter
//                                    })
                        }
                        postDelayed({
                            scrollToPosition(
                                when (resources.configuration.orientation) {
                                    Configuration.ORIENTATION_PORTRAIT -> instanceSettings.lastCompletelyVisible
                                    else -> instanceSettings.firstCompletelyVisible
                                }
                            )
                        }, 200)
                    }
                }
            }
        }
    }

    private fun onScrollListener() {
        with(binding.recyclerGalleryFragment) {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0) {
                        instanceSettingsViewModel?.let { instanceSettings ->
                            dataViewModel?.let { data ->
                                val glManager = layoutManager as GridLayoutManager

                                instanceSettings.firstCompletelyVisible =
                                    glManager.findFirstCompletelyVisibleItemPosition()
                                instanceSettings.lastCompletelyVisible =
                                    glManager.findLastCompletelyVisibleItemPosition()

                                if (glManager.itemCount != instanceSettings.previousItemCount)
                                    instanceSettings.loadingStatus = 0
                                if (instanceSettings.loadingStatus == 0 &&
                                    glManager.findLastVisibleItemPosition() >= glManager.itemCount - 1) {

                                    instanceSettings.currentPage = (instanceSettings.firstPage +
                                            ceil(data.itemsCount / instanceSettings.loadLimit.toDouble()).toInt())


                                    thread {
                                        while (true) {
                                            Log.e(TAG, "currentPage = ${instanceSettings.currentPage}")
                                            Log.e(TAG, "firstCompletelyVisible = ${instanceSettings.firstCompletelyVisible}")
                                            Log.e(TAG, "lastCompletelyVisible = ${instanceSettings.lastCompletelyVisible}")
                                            Log.e(TAG, "scaleType = ${persistentSettingsViewModel?.getScaleType()}")
                                            sleep(5000)
                                        }
                                    }

                                    instanceSettings.previousItemCount = glManager.itemCount
                                    instanceSettings.loadingStatus = 1
                                    loadMoreDataAsync(instanceSettings.currentPage)
                                }
                            }
                        }
                    }
                }
            })
        }
    }

    private fun onToolbarClickListener() {
/*        binding.galleryToolbar.setOnMenuItemClickListener {
*//*            if (it.itemId == R.id.item_shuffle) {
                settings?.let {
                    val newSettings = Settings()
                    val totalPages = it.totalPages

                    newSettings.firstPage = (1..totalPages).random()
//                    Log.e("${TAG} toolbar", newSettings.firstPage.toString())
                    it.deepCopy(newSettings)
                    communicator?.openGalleryFragment()
                }
            }
            true*//*
            if (it.itemId == R.id.item_settings) {
                communicator?.openSettingsActivity()
            }
            true
        }*/
    }

    private fun setObservers() {
        persistentSettingsViewModel?.let { persistentSettings ->
            persistentSettings.getPersistentSettings()?.observe(viewLifecycleOwner, {
                if (it != null) {
                    dataViewModel?.dataAdapter?.scaleType = persistentSettings.getScaleType()
                    dataViewModel?.dataAdapter?.quality = "200"
                    Log.e(TAG, "st = ${persistentSettings.getScaleType()}")
                    Log.e(TAG, "quality = ${dataViewModel?.dataAdapter?.quality}")

                }
            })
        }
    }

    private fun loadMoreDataAsync(page: Int) {
        dataViewModel?.let {
            data ->
            instanceSettingsViewModel?.let {
                instanceSettings ->
                data.loadData(
                    page,
                    instanceSettings.loadLimit,
                    instanceSettings.fields)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            GalleryFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}