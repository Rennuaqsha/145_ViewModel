package com.example.prak5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prak5.data.DataForm
import com.example.prak5.data.DataSource
import com.example.prak5.ui.theme.Prak5Theme
import com.example.praktikum_5.ui.theme.CobaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Prak5Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TopHeader()

                    TampilLayout()
                }
            }
        }
    }
}
@Composable
fun TopHeader() {
    ElevatedCard (
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            Image(painter = painterResource(id = R.drawable.baseline_arrow_back_24), contentDescription = "")
            Text(text = "Register",
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp)

            )
        }
    }

    }

@Composable
fun TextHasil(namanya: String, telponnya: String, Alamatnya: String, Email: String, jenisnya: String)
{
    ElevatedCard (
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier.fillMaxWidth()
    ){
        Text(text = "Nama : " + namanya,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
        Text(text = "Telepon : " + telponnya,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        )
        Text(text = "Alamat : " + Alamatnya,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        )
        Text(text = "Email : " + Email,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        )
        Text(text = "Jenis Kelamin : " + jenisnya,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        )

    }
}
@Composable
fun SelectJK(
    option: List<String>,
    onSelectionChange: (String) ->Unit = {}
){
    Column {
        Text(text = "Jenis Kelamin :" )
    }
    var selectedValue by rememberSaveable { mutableStateOf("") }

    Row (modifier = Modifier.padding(16.dp))
    {
        option.forEach{ item ->
            Row (
                modifier = Modifier.selectable(
                    selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChange(item)
                    }
                ),
                verticalAlignment = Alignment.CenterVertically
            ){
                RadioButton(selected = selectedValue == item, onClick = {selectedValue = item
                    onSelectionChange(item)})
                Text(item)
            }
        }
    }
}

@Composable
fun SelectStat ( option: List<String>,
                 onSelectionChange: (String) ->Unit = {}
) {
    Column {
        Text(text = "Status :" )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TampilForm(cobaViewModel: CobaViewModel = viewModel()){

    var textNama by remember { mutableStateOf("") }
    var textTlp by remember { mutableStateOf("") }
    var textAlamat by remember { mutableStateOf("") }
    var textEmail by remember { mutableStateOf("") }

    val context = LocalContext.current
    val dataform: DataForm
    val uiState by cobaViewModel.uiState.collectAsState()
    dataform = uiState

    OutlinedTextField(value = textNama,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Username")},
        onValueChange = {textNama = it})

    OutlinedTextField(value = textTlp,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Telpon")},
        onValueChange = {textTlp = it})



    OutlinedTextField(value = textEmail,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Email")},
        onValueChange = {textEmail = it})

    SelectJK(option = DataSource.jenis.map { id -> context.resources.getString(id)},
        onSelectionChange = {cobaViewModel.setJenisK(it)})
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            cobaViewModel.insertData(textNama, textTlp,textAlamat,textEmail, dataform.sex)
        }){
        Text(text = stringResource(R.string.submit),
            fontSize = 16.sp)
    }
    Spacer(modifier = Modifier.height(100.dp))
    TextHasil(
        namanya = cobaViewModel.namaUsr,
        telponnya = cobaViewModel.noTelp,
        Alamatnya = cobaViewModel.alamat,
        Email = cobaViewModel.Email,
        jenisnya = cobaViewModel.jenisKl

        )

}

@Composable
fun TampilLayout(
    modifier: Modifier = Modifier
){
    Card(
        modifier = Modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            TopHeader()

            TampilForm()

        }

    }
}

