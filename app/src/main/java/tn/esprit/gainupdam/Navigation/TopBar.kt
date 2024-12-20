package tn.esprit.gainupdam.Navigation

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.NightsStay
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import tn.esprit.gainupdam.R
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TopBar(navController: NavHostController, onThemeToggle: () -> Unit) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var isMenuExpanded by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    // Move isSystemInDarkTheme() into the Composable function
    var mutableStateOf = mutableStateOf(isSystemInDarkTheme())
    var isDarkMode by rememberSaveable { mutableStateOf }

    val context = LocalContext.current

    if (showDatePicker) {
        SimpleDatePickerDialog(
            selectedDate = selectedDate,
            onDateSelected = { date ->
                selectedDate = date
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF03224c))
            .padding(13.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Calendar",
                    tint = Color.White,
                    modifier = Modifier.clickable { showDatePicker = true }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = selectedDate.format(DateTimeFormatter.ofPattern("EEEE")),
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Text(
                        text = selectedDate.format(DateTimeFormatter.ofPattern("dd MMMM")),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Box(contentAlignment = Alignment.TopEnd) {
                IconButton(onClick = { isMenuExpanded = !isMenuExpanded }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_menu),
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }
                DropdownMenu(
                    expanded = isMenuExpanded,
                    onDismissRequest = { isMenuExpanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .border(4.dp, Color(0xFF2196F3), RoundedCornerShape(9.dp)),
                    offset = DpOffset(x = 0.dp, y = 0.dp)
                ) {
                    DropdownMenuItem(
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                        text = { Text("My Profile", color = Color.Black) },
                        onClick = {
                            navController.navigate("profileScreen/{userId}/{name}/{email}")
                            isMenuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        leadingIcon = { Icon(Icons.Default.Language, contentDescription = "Language") },
                        text = { Text("Language", color = Color.Black) },
                        onClick = { isMenuExpanded = false }
                    )
                    DropdownMenuItem(
                        leadingIcon = { Icon(Icons.Default.Star, contentDescription = "Rate Us") },
                        text = { Text("Rate Us", color = Color.Black) },
                        onClick = { isMenuExpanded = false }
                    )
                    DropdownMenuItem(
                        leadingIcon = { Icon(Icons.Default.Share, contentDescription = "Share App") },
                        text = { Text("Share App", color = Color.Black) },
                        onClick = { isMenuExpanded = false }
                    )
                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(
                                imageVector = if (isDarkMode) Icons.Outlined.NightsStay else Icons.Default.WbSunny,
                                contentDescription = if (isDarkMode) "Light Mode" else "Dark Mode"
                            )
                        },
                        text = { Text(if (isDarkMode) "Light Mode" else "Dark Mode", color = Color.Black) },
                        onClick = {
                            onThemeToggle()
                            isDarkMode = !isDarkMode
                            val mode = if (isDarkMode) {
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                                "Dark Mode"
                            } else {
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                                "Light Mode"
                            }
                            Toast.makeText(context, "Switched to $mode", Toast.LENGTH_SHORT).show()
                            isMenuExpanded = false
                        }
                    )
                }
            }
        }
        DateSelector(selectedDate) { date ->
            selectedDate = date
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateSelector(selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val dates = List(8) { LocalDate.now().plusDays(it.toLong()) }
        items(dates) { date ->
            DateCard(date = date, isSelected = date == selectedDate) {
                onDateSelected(date)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateCard(date: LocalDate, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF2196F3) else Color(0xFF0a1728)
    )
    Card(
        modifier = Modifier
            .width(60.dp)
            .height(80.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            Text(
                text = date.format(DateTimeFormatter.ofPattern("EEE")),
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SimpleDatePickerDialog(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    var currentMonth by remember { mutableStateOf(YearMonth.from(selectedDate)) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(4.dp, Color(0xFF2196F3), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Month/Year header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        currentMonth = currentMonth.minusMonths(1)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_previous),
                            contentDescription = "Previous month"
                        )
                    }

                    Text(
                        text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                        style = MaterialTheme.typography.titleMedium
                    )

                    IconButton(onClick = {
                        currentMonth = currentMonth.plusMonths(1)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_next),
                            contentDescription = "Next month"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Days of week header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su").forEach { day ->
                        Text(
                            text = day,
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Calendar grid
                val daysInMonth = currentMonth.lengthOfMonth()
                val firstDayOfMonth = currentMonth.atDay(1)
                val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value

                Column {
                    var dayCounter = 1
                    repeat(6) { week ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            repeat(7) { dayOfWeek ->
                                val dayNumber = week * 7 + dayOfWeek + 1 - (firstDayOfWeek - 1)
                                if (dayNumber in 1..daysInMonth) {
                                    val date = currentMonth.atDay(dayNumber)
                                    val isSelected = date == selectedDate

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .aspectRatio(1f)
                                            .padding(2.dp)
                                            .background(
                                                if (isSelected) Color(0xFF2196F3) else Color.Transparent,
                                                RoundedCornerShape(4.dp)
                                            )
                                            .clickable { onDateSelected(date) },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = dayNumber.toString(),
                                            color = if (isSelected) Color.White else Color.Black
                                        )
                                    }
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .aspectRatio(1f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
