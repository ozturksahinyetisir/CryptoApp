package com.ozturksahinyetisir.cryptoapp.view.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.ozturksahinyetisir.cryptoapp.R
import com.ozturksahinyetisir.cryptoapp.databinding.FragmentSplashBinding
import com.ozturksahinyetisir.cryptoapp.view.home.HomeFragment

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_splash, container, false
        )
        binding.viewModel = SplashScreenViewModel()
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = SplashScreenPagerAdapter(this)

        /**
         * ViewPager2 position if at last page, set arrow icon is visible
         */
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 2) {
                    binding.viewModel?.showArrowIcon()
                    // Handler used for auto transfer fragment after 3 seconds.
                    Handler(Looper.getMainLooper()).postDelayed({
                        val homeFragment = HomeFragment()
                        transferTo(homeFragment)
                    }, 3000)
                } else {
                    binding.viewModel?.hideArrowIcon()
                }
            }
        })

        /**
         * press button for transfer to HomeFragment
         */
        binding.arrowIcon.setOnClickListener {
            val homeFragment = HomeFragment()
            transferTo(homeFragment)
        }
    }

    /**
     * function of transfering fragments
     */
    fun transferTo(fragment: Fragment) {
        requireActivity().supportFragmentManager.commit {
            addToBackStack("frag")
            setCustomAnimations(
                androidx.appcompat.R.anim.abc_slide_in_bottom,
                androidx.appcompat.R.anim.abc_fade_out,
                androidx.appcompat.R.anim.abc_fade_in,
                androidx.appcompat.R.anim.abc_slide_out_bottom
            )
            replace(R.id.nav_container, fragment)
        }
    }
}
