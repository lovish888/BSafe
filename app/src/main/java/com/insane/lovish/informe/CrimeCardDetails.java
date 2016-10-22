package com.insane.lovish.informe;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class CrimeCardDetails extends AppCompatActivity {
    Intent intent;
    TextView title, author, timePassed, description;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_card_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        image = (ImageView) findViewById(R.id.crime_image);
        title = (TextView) findViewById(R.id.crime_title);
        author = (TextView) findViewById(R.id.crime_author);
        timePassed = (TextView) findViewById(R.id.time_passed);
        description = (TextView) findViewById(R.id.description);

        intent = new Intent();
        int x = intent.getIntExtra("position", 0);
        Log.d("Filter", "X= " + x);
        if (x == 0) {
            image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.story1));
            title.setText("Gold chain snatched");
            author.setText("Express new service");
            timePassed.setText("8h");
            description.setText("TWO UNIDENTIFIED persons on a bike allegedly snatched the gold chain from an elderly woman of Sector 9 in broad daylight after entering her house here on Tuesday.\n" +
                    "The incident occurred around 11 am. When Shashi Goel, 65, was standing on the veranda of her house, two youths came on a bike and halted a few steps away from the house. One of them got down and asked her about a house number. She gave the directions and the youth left.\n" +
                    "“I went inside the house and after 5-10 minutes, the doorbell rang. He was the same youth who had asked the address before and as soon as I opened the door, he grabbed my chain. There were three gold chains, one of which he managed to snatch, and fled,” said Goel. “How can anyone expect to be safe, when such an incident can happen in broad daylight?”\n" +
                    "In her complaint to the police, Goel stated that the gold chain, weighing 1 tola, was worth over Rs 35,000. “She was wearing three gold chains, out of which, the accused managed to flee with one, which was of lesser value, while the other two got broken,” said the police.\n" +
                    "\n" +
                    "\n" +
                    "  \n" +
                    "A case under Section 379A (snatching) was registered at Sector 10 police station. The police got the CCTV footage from the cameras installed at a nearby house and were trying to identify the accused.\n" +
                    "“We are analysing the footage. The victim has identified the youth, and the search is on to trace him. They had come on a Pulsar bike which did not carry any registration number plate,” said the police post incharge, Sector 10.\n");

        } else if (x == 1) {
            image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.story2));
            title.setText("Truck rans into tourist bus");
            author.setText("Express new service");
            timePassed.setText("10h");
            description.setText("AS MANY as 48 people were injured when a Delhi-bound tourist bus coming from Jammu was hit by a rashly driven truck on Zirakpur-Banur road in the early hours of Tuesday. The injured were admitted to Government Medical College and Hospital (GMCH), Sector 32. Six passengers who suffered serious injuries were referred to PGI.\n" +
                    "According to the police, the accident happened around 3 am on Tuesday. When the bus reached the Zirakpur-Patiala highway, it was hit by the truck which was loaded with LPG cylinders and was coming from Banur and heading towards Zirakpur.\n" +
                    "Investigating officer Narinder Kumar said that the collision was so powerful that the bus overturned and fell in the pit along the road. A police team rushed to the spot on receiving the information and found that all the passengers were stranded inside the bus, following which a rescue operation was started.\n" +
                    "The IO said that cranes were brought from Banur. Ambulances from Panchkula, Mohali, Chandigarh, Derabassi, Lalru and Dhakoli, too, reached the spot and ferried the patients to different hospitals in Zirakpur and Chandigarh.\n" +
                    "According to the police, 60 passengers, including some children, were travelling in the bus. While 32 passengers were taken to GMCH, Sector 32, 10 patients were admitted to Derabassi civil hospital. Six patients were rushed to PGI. The truck driver managed to escape. Out of the 48 injured, 39 were discharged after the treatment.\n" +
                    "Zirakpur SHO Tarlochan Singh said that they had booked the truck driver but he was not identified yet. They had impounded the truck.\n");

        } else if (x == 2) {
            image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.story3));
            title.setText("Businessman robbed, then killed");
            author.setText("Express new service");
            timePassed.setText("12h");
            description.setText("A businessman was beaten to death after being robbed of Rs 15,000 at Beldanga area in Murshidabad district.\n" +
                    "The incident took place at around 9 pm Wednesday night when Hafizul Sheikh, a fruit seller, was returning home on his cycle after the day’s work when he was confronted by a group of 5-6 persons. They beat him up and snatched away Rs 15,000 from his possession. Police said Hafizul was later beaten to death, most likely because he had identified some of his attackers. He died on the spot.\n" +
                    "Hafizul had intended to buy a smartphone with the money that was stolen. “He had talked about it to many persons and chances are that some of them had killed him,” one of his family members said.\n" +
                    "Police said a case has been lodged in this connection but no arrests had been made yet.\n");

        }
    }
}
