package ru.kolyagin.allstock.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kolyagin.allstock.R
import ru.kolyagin.allstock.databinding.ActivityMainBinding
import ru.kolyagin.allstock.presentation.adapter.StockAdapter

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private val adapter = StockAdapter()
    private val subscribeChannel = Channel<List<String>>(Channel.BUFFERED)
    private val unsubscribeChannel = Channel<List<String>>(Channel.BUFFERED)
    private var lastItems = emptyList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.onCreate(subscribeChannel.receiveAsFlow(), unsubscribeChannel.receiveAsFlow())
        setupViews()
        setupViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    private fun setupViews() {
        binding.loadIndicator.visibility = View.VISIBLE
        binding.list.apply {
            visibility = View.INVISIBLE
            adapter = this@MainActivity.adapter
        }
        adapter.onPagesUpdatedFlow.onEach {
            unsubscribeChannel.send(lastItems)
            binding.loadIndicator.visibility = View.INVISIBLE
            binding.list.visibility = View.VISIBLE
            viewModel.loadInitialPrices(adapter.snapshot().items)
            subscribeChannel.send(adapter.snapshot().items.map { it.symbol })
            lastItems = adapter.snapshot().items.map { it.symbol }
        }.launchIn(lifecycleScope)
    }

    private fun setupViewModel() {
        viewModel.eventsFlow.flowWithLifecycle(
            lifecycle,
            Lifecycle.State.STARTED
        ).onEach {
            Toast.makeText(this@MainActivity, getString(R.string.error), Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)

        viewModel.listOfSymbolsFlow.flowWithLifecycle(
            lifecycle,
            Lifecycle.State.STARTED
        ).onEach { pagingData ->
            unsubscribeChannel.send(adapter.snapshot().items.map { it.symbol })
            adapter.submitData(lifecycle, pagingData)
        }.launchIn(lifecycleScope)

        viewModel.priceFlow.flowWithLifecycle(
            lifecycle,
            Lifecycle.State.STARTED
        ).onEach {
            adapter.updateItem(it.symbol, it.price)
        }.launchIn(lifecycleScope)

    }
}