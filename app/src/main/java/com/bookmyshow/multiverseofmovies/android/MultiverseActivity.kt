package com.bookmyshow.multiverseofmovies.android

import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsSession
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.bookmyshow.multiverseofmovies.R
import com.bookmyshow.multiverseofmovies.android.adapters.CastRecyclerAdapter
import com.bookmyshow.multiverseofmovies.android.adapters.CrewRecyclerAdapter
import com.bookmyshow.multiverseofmovies.android.adapters.LanguageRecyclerAdapter
import com.bookmyshow.multiverseofmovies.android.adapters.ProductionRecyclerAdapter
import com.bookmyshow.multiverseofmovies.android.usecases.MultiverseCastCrewResult.MultiverseCastCrewFailure
import com.bookmyshow.multiverseofmovies.android.usecases.MultiverseCastCrewResult.MultiverseCastCrewSuccess
import com.bookmyshow.multiverseofmovies.android.usecases.MultiverseResult.MultiverseFailure
import com.bookmyshow.multiverseofmovies.android.usecases.MultiverseResult.MultiverseSuccess
import com.bookmyshow.multiverseofmovies.android.viewmodel.MultiverseViewModel
import com.bookmyshow.multiverseofmovies.base_framework.network.Response
import com.bookmyshow.multiverseofmovies.databinding.ActivityMultiverseBinding
import com.bookmyshow.multiverseofmovies.utils.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MultiverseActivity : AppCompatActivity() {

    private val viewModel by viewModels<MultiverseViewModel>()
    private lateinit var languageRecyclerAdapter: LanguageRecyclerAdapter
    private lateinit var productionRecyclerAdapter: ProductionRecyclerAdapter
    private lateinit var crewRecyclerAdapter: CrewRecyclerAdapter
    private lateinit var castRecyclerAdapter: CastRecyclerAdapter
    private val binding by viewBinding(ActivityMultiverseBinding::inflate)
    private fun <T : ViewBinding> initBinding(binding: T): View {
        return with(binding) {
            root
        }
    }

    private var mCustomTabsSession: CustomTabsSession? = null

    private var urlForHomePage = "www.google.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initBinding(binding))
        setupViews()
        observeViewModels()
        viewModel.getMultiverseDetails()
        viewModel.getMultiverseCastCrewDetails()
    }

    private fun setupViews() {

        languageRecyclerAdapter = LanguageRecyclerAdapter()
        productionRecyclerAdapter = ProductionRecyclerAdapter(this@MultiverseActivity)

        castRecyclerAdapter = CastRecyclerAdapter(this@MultiverseActivity)
        crewRecyclerAdapter = CrewRecyclerAdapter(this@MultiverseActivity)
        binding.apply {
            languageRecyclerView.layoutManager = LinearLayoutManager(this@MultiverseActivity,LinearLayoutManager.HORIZONTAL,false)
            languageRecyclerView.adapter = languageRecyclerAdapter

            productionRecyclerView.layoutManager = LinearLayoutManager(this@MultiverseActivity,LinearLayoutManager.HORIZONTAL,false)
            productionRecyclerView.adapter = productionRecyclerAdapter

            crewRecyclerView.layoutManager =LinearLayoutManager(this@MultiverseActivity,LinearLayoutManager.HORIZONTAL,false)
            crewRecyclerView.adapter = crewRecyclerAdapter

            castRecyclerView.layoutManager = LinearLayoutManager(this@MultiverseActivity,LinearLayoutManager.HORIZONTAL,false)
            castRecyclerView.adapter = castRecyclerAdapter

            buttonBookTickets.setOnClickListener {
                Toast.makeText(this@MultiverseActivity, "Booking Tickets", Toast.LENGTH_SHORT).show()
            }

            textViewGoToHomepage.setOnClickListener {
                loadCustomTabForSite(urlForHomePage)
            }
            readMoreTextView.setOnClickListener {
                readMoreTextView.paintFlags = readMoreTextView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                if (readMoreTextView.text == "Read more"){
                    readMoreTextView.text = getString(R.string.less)
                    textViewOverView.maxLines = Integer.MAX_VALUE
                }
                else
                {
                    readMoreTextView.text = getString(R.string.read_more)
                    textViewOverView.maxLines = 2
                }
            }
        }
    }
    private fun observeViewModels() {
        viewModel.multiverseLiveData
            .observe(this) { result ->
                when (result.status) {
                    Response.Status.LOADING -> {

                    }
                    Response.Status.SUCCESS -> {
                        when (result.data) {
                            is MultiverseSuccess -> {
                                val multiverseData = (result.data).multiverseResponse



                                languageRecyclerAdapter.setUpDatedData(multiverseData.spokenLanguages)
                                productionRecyclerAdapter.setUpDatedData(multiverseData.productionCompanies)
                                binding.apply {
                                    val posterUrl = "https://image.tmdb.org/t/p/original/" + multiverseData.backdropPath
                                    var genres1  = ""
                                    multiverseData.genres.forEach{
                                        genres1 += it.name+", "
                                    }
                                    Glide
                                        .with(this@MultiverseActivity)
                                        .load(posterUrl)
                                        .placeholder(R.drawable.langaude_background)
                                        .into(imageViewPoster)

                                    textViewTitle.text = multiverseData.originalTitle
                                    val time = formatHoursAndMinutes(multiverseData.runtime)
                                    textViewOverView.text = multiverseData.overview
                                    textViewGenre.text = getString(R.string.text_genre_time_date , multiverseData.releaseDate , genres1 , time)

                                    val voteAvg:Double = String.format("%.1f", multiverseData.voteAverage).toDouble()

                                    urlForHomePage = multiverseData.homepage
                                    textViewTitle.text = multiverseData.title
                                    textViewRatings.text = getString(R.string.text_ratings ,voteAvg.toString() )
                                    textViewVotes.text = getString(R.string.text_votes, multiverseData.voteCount.toString())
                                }


                            }
                            is MultiverseFailure -> {

                                Toast.makeText(this,
                                    (result.data).errorMessage, Toast.LENGTH_SHORT).show()
                            }
                            else -> {

                                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    Response.Status.ERROR -> {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        viewModel.multiverseCastCrewLiveData
            .observe(this){result ->
                when (result.status) {
                    Response.Status.LOADING -> {

                    }
                    Response.Status.SUCCESS -> {
                        when (result.data) {
                            is MultiverseCastCrewSuccess -> {
                                val multiverseCastCrewData = (result.data).multiverseCastResponse

                                crewRecyclerAdapter.setUpDatedData(multiverseCastCrewData.crew)
                                castRecyclerAdapter.setUpDatedData(multiverseCastCrewData.cast)

                            }
                            is MultiverseCastCrewFailure -> {

                                Toast.makeText(this,
                                    (result.data).errorMessage, Toast.LENGTH_SHORT).show()
                            }
                            else -> {

                                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    Response.Status.ERROR -> {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }

            }
    }

    //Function to Load Custom Chrome Tab
    private fun loadCustomTabForSite(url: String, color: Int = Color.WHITE) {
        val customTabsIntent = CustomTabsIntent.Builder(mCustomTabsSession)
            .setToolbarColor(color)
            .setShowTitle(true)
            .build()
        customTabsIntent.launchUrl(this@MultiverseActivity, Uri.parse(url))
    }

    private fun formatHoursAndMinutes(totalMinutes: Int): String {
        var minutes = (totalMinutes % 60).toString()
        minutes = if (minutes.length == 1) "0$minutes" else minutes
        return (totalMinutes / 60).toString() + "h " + minutes +"m"
    }
}