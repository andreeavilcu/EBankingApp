package com.example.ebankingapp.presentation.currency

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ebankingapp.domain.model.CurrencyModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyScreen(
    navController: NavController,
    viewModel: CurrencyViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar ={
            TopAppBar(title = { Text("Currency exchange") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ){
            OutlinedTextField(
                value = state.amountInRon,
                onValueChange = { viewModel.onAmountChange(it) },
                label = { Text("Amount (RON)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            if(state.isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn {
                    items(state.currencies) { currency ->
                        CurrencyItem(currency)
                    }
                }
            }
        }
    }
}

@Composable
fun CurrencyItem(item: CurrencyModel){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ){
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = item.code, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Column(horizontalAlignment = androidx.compose.ui.Alignment.End) {
                Text(text = String.format("%.2f", item.convertedValue), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = "Rata: ${item.value}", fontSize = 12.sp, color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}