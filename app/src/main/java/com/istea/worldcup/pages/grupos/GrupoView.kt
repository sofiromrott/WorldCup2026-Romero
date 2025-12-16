package com.istea.worldcup.pages.grupos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.istea.worldcup.R
import com.istea.worldcup.domain.Group
import com.istea.worldcup.pages.grupos.GruposIntention.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GruposView(
    modifier: Modifier = Modifier,
    state: GruposState,
    onAction: (GruposIntention) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mundial 2026") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->

        Box(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            //  PUNTO 1: Añadir la imagen de fondo
            BackgroundImage()

            when (state) {
                GruposState.Cargando -> Cargando()
                is GruposState.Resultado -> GroupsList(grupos = state.grupos){
                    onAction(OnGrupoClick(it))
                }
                GruposState.Vacio -> Text(text = "")
            }
        }
    }
}

@Composable
fun GroupsList(grupos: List<Group>, onGrupoClick: (String) -> Unit){
    LazyColumn(Modifier.padding(16.dp)) {
        items(grupos) { grupo ->
            GroupCardView(grupo = grupo) {
                onGrupoClick(grupo.id)
            }
        }
    }
}

@Composable
fun GroupCardView(
    modifier: Modifier = Modifier,
    grupo: Group,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = grupo.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))
            grupo.teams.forEach { team ->
                Text(
                    //PUNTO 2: Usamos el nombre del equipo en lugar de "Chile"
                    text = team,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
private fun BackgroundImage() {
    Image(
        painter = painterResource(R.drawable.logo),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(vertical = 60.dp)
            .fillMaxSize()
    )
}

@Composable
private fun Cargando() {
    Box (
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator(
            modifier = Modifier.size(160.dp),
            strokeWidth = 20.dp,
            color = MaterialTheme.colorScheme.primaryContainer
        )
    }
}