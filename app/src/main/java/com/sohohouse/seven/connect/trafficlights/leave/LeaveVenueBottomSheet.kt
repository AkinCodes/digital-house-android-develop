package com.sohohouse.seven.connect.trafficlights.leaveimport android.os.Bundleimport android.view.Viewimport androidx.lifecycle.lifecycleScopeimport com.sohohouse.seven.Rimport com.sohohouse.seven.base.mvvm.BaseMVVMBottomSheetimport com.sohohouse.seven.common.analytics.AnalyticsManagerimport com.sohohouse.seven.common.dagger.Injectableimport com.sohohouse.seven.common.extensions.setFragmentResultimport com.sohohouse.seven.databinding.LeaveVenueBottomSheetBindingimport kotlinx.coroutines.flow.launchInimport kotlinx.coroutines.flow.onEachimport javax.inject.Injectclass LeaveVenueBottomSheet : BaseMVVMBottomSheet<LeaveVenueViewModel>(), Injectable {    override val viewModelClass: Class<LeaveVenueViewModel>        get() = LeaveVenueViewModel::class.java    override val contentLayout: Int        get() = R.layout.leave_venue_bottom_sheet    override val fixedHeight: Int        get() = (requireActivity().window.decorView.height * 0.27f).toInt()    @Inject    lateinit var analyticsManager: AnalyticsManager    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {        super.onViewCreated(view, savedInstanceState)        LeaveVenueBottomSheetBinding.bind(view).setup()    }    private fun LeaveVenueBottomSheetBinding.setup() {        cancel.setOnClickListener {            analyticsManager.logEventAction(AnalyticsManager.Action.TrafficLightsLeaveVenueCancel)            dismiss()        }        confirm.setOnClickListener { viewModel.confirm() }        viewModel.dismiss.onEach {            if (it) {                setFragmentResult(RESULT_TAG)                dismiss()            }        }.launchIn(lifecycleScope)        viewModel.title            .onEach { title.text = getString(R.string.you_re_leaving_house, it) }            .launchIn(lifecycleScope)    }    companion object {        const val RESULT_TAG = "res_leave_venue"    }}