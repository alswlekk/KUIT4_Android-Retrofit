package com.example.kuit4_android_retrofit

import RVPopularMenuAdapter
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kuit4_android_retrofit.data.CategoryData
import com.example.kuit4_android_retrofit.data.PopularData
import com.example.kuit4_android_retrofit.databinding.FragmentHomeBinding
import com.example.kuit4_android_retrofit.databinding.ItemCategoryBinding
import com.example.kuit4_android_retrofit.retrofit.RetrofitObject
import com.example.kuit4_android_retrofit.retrofit.service.CategoryService
import com.example.kuit4_android_retrofit.retrofit.service.HomePopularService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var popularAdapter: RVPopularMenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        fetchCategoryInfo()
        fetchPopularInfo()

        return binding.root
    }

    private fun fetchPopularInfo() {
        val service = RetrofitObject.retrofit.create(HomePopularService::class.java)
        val call = service.getPopular()

        call.enqueue(
            object : retrofit2.Callback<List<PopularData>> {
                override fun onResponse(
                    call: Call<List<PopularData>>,
                    response: Response<List<PopularData>>
                ) {
                    if(response.isSuccessful) {
                        val popularResponse = response.body()

                        if (!popularResponse.isNullOrEmpty()) {
                            showPopularInfo(popularResponse)
                        } else {
                            Log.d("빈값받아옴", "빈값받아옴")
                        }
                    } else {
                        Log.d("서버 응답 실패", "서버 응답 실패")
                    }
                }

                override fun onFailure(call: Call<List<PopularData>>, t: Throwable) {
                    Log.d("실패3", "실패3")
                }

            }
        )
    }

    private fun fetchCategoryInfo() {
        val service = RetrofitObject.retrofit.create(CategoryService::class.java)
        val call = service.getCategories()

        call.enqueue(
            object : retrofit2.Callback<List<CategoryData>> {
                override fun onResponse(
                    call: Call<List<CategoryData>>,
                    response: Response<List<CategoryData>>
                ) {
                    if (response.isSuccessful) {
                        val categoryResponse = response.body()

                        // 데이터가 성공적으로 받아와졌을 때
                        if (!categoryResponse.isNullOrEmpty()) {
                            showCategoryInfo(categoryResponse)
                        } else {
                            Log.d("빈값받아옴", "빈값받아옴")
                            // 빈 값을 받아온 경우
                        }
                    } else {
                        Log.d("서버 응답 실패", "서버 응답 실패")
                        // 서버 응답이 실패했을 때 (상태 코드 5**)
                    }
                }

                override fun onFailure(call: Call<List<CategoryData>>, t: Throwable) {
                    Log.d("실패3", "실패3")
                }

            }
        )
    }

    private fun showCategoryInfo(categoryList: List<CategoryData>) {
        // 레이아웃 인플레이터를 사용해 카테고리 항목을 동적으로 추가
        val inflater = LayoutInflater.from(requireContext())
        binding.llMainMenuCategory.removeAllViews() // 기존 항목 제거

        categoryList.forEach { category ->
            val categoryBinding =
                ItemCategoryBinding.inflate(inflater, binding.llMainMenuCategory, false)

            // 이미지 로딩: Glide 사용 (이미지 URL을 ImageView에 로드)
            Glide
                .with(this)
                .load(category.categoryImg)
                .into(categoryBinding.sivCategoryImg)

            // 카테고리 이름 설정
            categoryBinding.tvCategoryName.text = category.categoryName

            // 레이아웃에 카테고리 항목 추가
            binding.llMainMenuCategory.addView(categoryBinding.root)
        }
    }

    private fun showPopularInfo(popularList: List<PopularData>) {
        popularAdapter = RVPopularMenuAdapter(popularList)
        binding.rvMainPopularMenus.adapter = popularAdapter
        binding.rvMainPopularMenus.layoutManager = LinearLayoutManager(requireContext())
        popularAdapter.notifyDataSetChanged()

    }
}
