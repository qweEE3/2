package com.example.second

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.second.ui.theme.SecondTheme
import java.lang.Exception
import java.lang.NumberFormatException
import java.math.RoundingMode
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    private var subScript = SpanStyle(
        baselineShift = BaselineShift.Subscript,
        fontSize = 16.sp,
        color = Color.Black,
    )

    private var superScript = SpanStyle(
        baselineShift = BaselineShift.Superscript,
        fontSize = 16.sp,
        color = Color.Black,
    )

    private fun getResult(a: Double,b: Double, c:Double): DoubleArray{
        var result: DoubleArray = DoubleArray(2)
        var disc: Double = b*b-4*a*c
        if(disc < 0){
            result[0] = -1.0
        } else {
            try {
                var x1: Double = (-b+sqrt(disc))/(2*a)
                var x2: Double = (-b-sqrt(disc))/(2*a)
                result[0] = x1.toBigDecimal().setScale(2,RoundingMode.UP).toDouble()
                result[1] = x2.toBigDecimal().setScale(2,RoundingMode.UP).toDouble()
            }
            catch (e: Exception){
                result[0] = -1.0
            }
        }
        return result
    }


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var a = remember {
                mutableStateOf("")
            }
            BaselineShift.Superscript
            var b = remember {
                mutableStateOf("")
            }
            var c = remember {
                mutableStateOf("")
            }
            var result = remember{
                mutableStateOf("")
            }
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize(),
                Arrangement.Center,
                Alignment.CenterHorizontally
            ){
                Text(
                    fontSize = 25.sp,
                    text = buildAnnotatedString {
                        append("ax")
                        withStyle(superScript){
                            append("2")
                        }
                        append(" + bx + c = 0")
                    }
                )
                Row{
                    Column {
                        Row{
                            Text(
                                fontSize = 25.sp,
                                text = "a:"
                            )
                            TextField(
                                textStyle = TextStyle(fontSize = 15.sp),
                                modifier = Modifier.border(BorderStroke(2.dp,color = Color.Black)).size(width=70.dp, height = 49.dp),
                                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                                value = a.value,
                                onValueChange = {t->
                                    a.value = t
                                    result.value = ""
                                },
                            )
                            Text(
                                fontSize = 25.sp,
                                text = "b:"
                            )
                            TextField(
                                textStyle = TextStyle(fontSize = 15.sp),
                                modifier = Modifier.border(BorderStroke(2.dp,color = Color.Black)).size(width=70.dp, height = 49.dp),
                                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                                value = b.value,
                                onValueChange = {t->
                                    b.value = t
                                    result.value = ""
                                },
                            )
                            Text(
                                fontSize = 25.sp,
                                text = "c:"
                            )
                            TextField(
                                textStyle = TextStyle(fontSize = 15.sp),
                                modifier = Modifier.border(BorderStroke(2.dp,color = Color.Black)).size(width=70.dp, height = 49.dp),
                                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                                value = c.value,
                                onValueChange = {t->
                                    c.value = t
                                    result.value = ""
                                },
                            )
                        }
                    }

                }
                Button(
                    modifier = Modifier.padding(5.dp),
                    colors = ButtonDefaults.buttonColors(Color.White,Color.Black,Color.White,Color.White),
                    border = BorderStroke(1.dp,color = Color.Black),
                    onClick = {
                        if(a.value == "" || b.value == "" || c.value == ""){
                            result.value = "не все поля заполнены"

                        } else{
                            try{
                                var roots = getResult(a.value.toDouble(),b.value.toDouble(),c.value.toDouble())
                                if(roots[0] == -1.0){
                                    result.value = "уравнение не имеет действительных корней"
                                }else if(roots[0] == roots[1]){
                                    result.value = "x1=x2=${roots[0]}"
                                } else {
                                    result.value = "x1=${roots[0]}, x2=${roots[1]}"
                                }

                            } catch (e: Exception){
                                result.value = "только числовые значения"
                                a.value = ""
                                b.value = ""
                                c.value = ""
                            }
                        }


                }) {
                    Text("ответ", fontSize = 25.sp)
                }
                Text(
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    text = result.value
                )

            }
        }
    }
}
