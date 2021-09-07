package com.example.jetpackcomposebasic

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposebasic.ui.theme.JetpackComposeBasicTheme

class ModulDuaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            /** Masukkan Function [MessageCard] pada [Surface] untuk Mengubah Background
             *
            Surface(color = MaterialTheme.colors.background){
            MessageCard(Message("Supri", "Bagaimana Kabar mu ?"))
            }
             */

            /**
             MessageCard(Message("Supri", "Bagaimana Kabar mu ?"))
             */

            JetpackComposeBasicTheme {
                Conversation(SampleData.conversationSample)
            }
        }
    }
}

data class Message(val author: String, val body : String)

@Composable
fun MessageCard(msg: Message){
    Row {
       Image(
           painter = painterResource(id = R.drawable.profil),
           contentDescription = "Supriyadi Image Kid",
           modifier = Modifier
               // memberi jarak pada setiap sisi image
               .padding(5.dp)
               //Set Image Size
               .size(40.dp)
               //to Make Image Circle
               .clip(CircleShape)
               // Memberi garis tepi pada circle image dengan warna
               .border(2.dp, MaterialTheme.colors.secondary, CircleShape))

        /** Memberi Space horizontal antara [Image] dan [Column] */
        Spacer(modifier = Modifier.width(8.dp))

        /** Membuat Expandable Text */
        var isExpanded by remember { mutableStateOf(false) }
        
        /** Ketika text body di klik maka background berubah */
        val surfaceColor: Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                //memberi warna pada text
                color = MaterialTheme.colors.secondaryVariant,
                //memberi jarak hanya pada sisi top
            modifier = Modifier.padding(top = 5.dp),
                //mengubah gaya penulisan
            style = MaterialTheme.typography.subtitle2)

            /** Memberi Space secara Vertical antara [Text] Pertama dan [Text] Kedua */
            Spacer(modifier = Modifier.height(4.dp))

            Surface(shape = MaterialTheme.shapes.medium, elevation = 1.dp,
            // Penerapan Background ketika isExpanded
            color = surfaceColor,
                modifier = Modifier.animateContentSize().padding(3.dp)
            ){
                Text(text = msg.body,
                    //memberi padding/jarak hanya pada sisi kanan/end
                    modifier = Modifier.padding(end = 5.dp),
                    style = MaterialTheme.typography.body2,
                    //if the message is expanded , we display all it content
                // otherwise we only display the first line
                maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    )
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>){
    LazyColumn{
        items(messages){ message ->
            MessageCard(message)
        }
    }
}

/** PREVIEW */

@Preview(
    //Menambahkan Mode Terang
name = "Mode Terang"
)
@Preview(
    //Menambahkan Tema Gelap
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Mode Gelap"
)
@Composable
fun PreviewMessageCard(){

    /**
    Surface(color = MaterialTheme.colors.background){
        MessageCard(msg = Message("CopyDet", "Hai, Apa kabar ?"))
    }
    */
    /** Hanya menampilkan Text [MessageCard] secara raw
    MessageCard(msg = Message("CopyDet", "Hai, Apa kabar ?"))
    */

    //Menggunakan Pilihan Warna Tema
    JetpackComposeBasicTheme {
        MessageCard(msg = Message("CopyDet", "Hai, Apa kabar ?"))
    }
}

@Preview
@Composable
fun PreviewConversation(){
    JetpackComposeBasicTheme {
        Conversation(SampleData.conversationSample)
    }
}
