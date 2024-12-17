package es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.firebase.Timestamp
import com.itextpdf.text.Document
import com.itextpdf.text.FontFactory
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import es.iesnervion.alopez.ourtravel.domain.repository.Destinations
import kotlinx.coroutines.Job
import java.io.File
import java.io.FileOutputStream
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.KFunction6


@Composable
fun TripPlanningTopBar(
    trip: TripPlanning,
    navigateToTripListScreen: () -> Unit,
    openDialog: () -> Unit,
    updateTripPlanning: KFunction6<String, String, Timestamp, Timestamp, Long, String?, Job>,
    nameUpdated: MutableState<Boolean>,
    destinations: Destinations
) {
    var nameState by remember { mutableStateOf(trip.name) }
    TopAppBar(
        title = {
            TextField(
                value = nameState!!,
                onValueChange = { nameState = it },
                singleLine = true,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        },
        navigationIcon = {
            IconButton(onClick = { navigateToTripListScreen() })
            {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        actions = { TripPlanningTopBarActions(openDialog, trip, destinations) },
    )

    LaunchedEffect(nameState) {
        if (nameState != trip.name) {
            updateTripPlanning(
                trip.id.toString(),
                nameState.toString(),
                trip.startDate!!,
                trip.endDate!!,
                trip.totalCost ?: 0,
                try {
                    destinations[0].cityPhoto
                } catch (e: Exception) {
                    ""
                }
            )
            nameUpdated.value = true
        }
    }
}

@Composable
fun TripPlanningTopBarActions(
    openDialog: () -> Unit,
    trip: TripPlanning,
    destinations: Destinations
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        IconButton(onClick = { generatePDF(context, trip, destinations) }, enabled = true)
        {
            Icon(
                Icons.Filled.Share,
                contentDescription = "Share",
                modifier = Modifier.size(24.dp)
            )
        }

        IconButton(onClick = { openDialog() })
        {
            Icon(
                Icons.Filled.Delete,
                contentDescription = "Delete",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


fun generatePDF(context: Context, trip: TripPlanning, destinations: Destinations) {

    val document = Document()
    val fileName = "${trip.name}.pdf"
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    val sfd = SimpleDateFormat("dd/MM/yyyy")
    val symbol = currencyFormat.currency?.symbol

    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            100
        )
    } else {
        val file = File(context.getExternalFilesDir(null), fileName)
        val uri = FileProvider.getUriForFile(context, context.packageName + ".fileprovider", file)
        PdfWriter.getInstance(document, FileOutputStream(file))

        try {
            document.open()
            document.add(Paragraph(trip.name, FontFactory.getFont(FontFactory.COURIER, 30f)).apply { alignment = Paragraph.ALIGN_CENTER })
            document.add(Paragraph("\n"))
            document.add(Paragraph("Fecha de Inicio: ${sfd.format(trip.startDate?.toDate() ?: Date())}"))
            document.add(Paragraph("Fecha de Fin: ${sfd.format(trip.endDate?.toDate() ?: Date())}"))
            document.add(Paragraph("\n"))
            document.add(Paragraph("Coste Total: ${trip.totalCost} $symbol"))
            document.add(Paragraph("\n"))
            document.add(Paragraph("Destinos:"))

            for (destination in destinations) {
                document.add(Paragraph("${destination.cityName}: ", FontFactory.getFont(FontFactory.HELVETICA, 23f)).apply { alignment = Paragraph.ALIGN_CENTER })
                document.add(Paragraph("\n"))
                document.add(Paragraph("Fecha de Inicio: ${sfd.format(destination.startDate ?: Date())}"))
                document.add(Paragraph("Fecha de Fin: ${sfd.format(destination.endDate ?: Date())}"))
                document.add(Paragraph("\n"))
                document.add(Paragraph("Costes:"))
                document.add(Paragraph("Coste en Alojamiento: ${destination.accomodationCosts} $symbol"))
                document.add(Paragraph("Coste en Transporte: ${destination.transportationCosts} $symbol"))
                document.add(Paragraph("Coste en Comida: ${destination.foodCosts} $symbol"))
                document.add(Paragraph("Coste en Turismo: ${destination.tourismCosts} $symbol"))
                document.add(Paragraph("\n"))
                document.add(Paragraph("Alojamiento: ${destination.travelStay}"))
                document.add(Paragraph("\n"))
                document.add(Paragraph("Descripción: ${destination.description}"))
                document.add(Paragraph("\n"))
                document.add(Paragraph("Atracciones Turísticas:"))

                var attractions = ""
                for (attraction in destination.tourismAttractions!!) {
                    attractions = "$attractions$attraction, "
                }
                val attractionsToShow = attractions.dropLast(2)
                document.add(Paragraph(attractionsToShow))

            }
        } catch (e: Exception) {
            Toast.makeText(context, "Ha ocurrido un error inesperado.", Toast.LENGTH_SHORT).show()
        } finally {
            document.close()
        }

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "application/pdf"
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        context.startActivity(Intent.createChooser(shareIntent, "Share PDF"))
    }
}
