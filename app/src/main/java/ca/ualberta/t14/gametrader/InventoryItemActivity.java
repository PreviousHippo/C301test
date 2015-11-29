package ca.ualberta.t14.gametrader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * Copyright (C) 2015  Aaron Arnason, Tianyu Hu, Michael Xi, Ryan Satyabrata, Joel Johnston, Suzanne Boulet, Ng Yuen Tung(Brigitte)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

public class InventoryItemActivity extends Activity {

    Game game;
    InventoryController inventorycontroller;
    User ownerProfile;
    TextView gameTitle;
    TextView platform;
    TextView condition;
    TextView owner;
    TextView additionalInfo;
    TextView phone;
    TextView address;
<<<<<<< HEAD
    ImageButton imageButton;
=======
    ImageView imageView;
    private Button tradeItemButton;
>>>>>>> de16a33d620c634edadf1b7de762d11220347048

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_item);

        game = (Game) ObjParseSingleton.getInstance().popObject("game");
        if( game == null) {
            game = new Game();
        }

        ownerProfile = (User) ObjParseSingleton.getInstance().popObject("gameOwner");
        if(ownerProfile == null) {
            throw new RuntimeException("Received null User for game owner.");
        }

        gameTitle = (TextView) findViewById(R.id.gameInfoTitle);
        platform = (TextView) findViewById(R.id.gameInfoConsole);
        condition = (TextView) findViewById(R.id.gameInfoCondition);
        owner = (TextView) findViewById(R.id.gameInfoOwner);
        additionalInfo = (TextView) findViewById(R.id.additionalInfoText);
        imageButton = (ImageButton) findViewById(R.id.inventoryItemImage);
        phone = (TextView) findViewById(R.id.phoneEditField);
        address = (TextView) findViewById(R.id.contactAddress);

        gameTitle.setText(game.getTitle());
        platform.setText(game.getPlatform().toString());
        condition.setText(game.getCondition().toString());
        owner.setText(ownerProfile.getUserName());
        phone.setText(ownerProfile.getPhoneNumber());
        address.setText(ownerProfile.getAddress());
        additionalInfo.setText(game.getAdditionalInfo());
        // Important, have to load bitmap from it's json first! Because bitmap is volatile.
        String imageJson = UserSingleton.getInstance().getUser().getPictureManager().loadImageJsonFromJsonFile(game.getPictureId(), getApplicationContext());
        if(!imageJson.isEmpty()) {
            game.setPictureFromJson(imageJson);
            imageButton.setImageBitmap(game.getPicture());
        }
        Button tradeItem  = (Button)findViewById(R.id.tradeButton);
        tradeItem.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                ObjParseSingleton.getInstance().addObject("game", game);

                Intent myIntent = new Intent(InventoryItemActivity.this, TradeActivity.class);

                startActivityForResult(myIntent, 1);

            }
        });

        final Button editGame = (Button)findViewById(R.id.buttonEditItem);
        if (!inventorycontroller.clonable(ownerProfile)){
            editGame.setText("Edit Game");
        }else{
            editGame.setText("Clone Game");
        };
        editGame.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (!inventorycontroller.clonable(ownerProfile)){
                    ObjParseSingleton.getInstance().addObject("game", game);
                    Intent myIntent = new Intent(InventoryItemActivity.this, EditInventoryItemActivity.class);
                    startActivityForResult(myIntent, 1);
                }else{
                    inventorycontroller.clone(game);
                };
            }
        });

        imageButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                ObjParseSingleton.getInstance().addObject("game", game);
                Intent myIntent = new Intent(InventoryItemActivity.this, InventoryItemPictureViewer.class);
                startActivityForResult(myIntent, 1);
            }
        });
        tradeItemButton = (Button) findViewById(R.id.tradeButton);
        tradeItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InventoryItemActivity.this, TradeActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK){
            finish();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        // todo: these should be observers shouldnt they?

        // When coming back to the activity and the data were updated.
        gameTitle.setText(game.getTitle());
        platform.setText(game.getPlatform().toString());
        condition.setText(game.getCondition().toString());
        owner.setText(ownerProfile.getUserName());
        phone.setText(ownerProfile.getPhoneNumber());
        address.setText(ownerProfile.getAddress());
        additionalInfo.setText(game.getAdditionalInfo());
        imageButton.setImageBitmap(game.getPicture());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inventory_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(InventoryItemActivity.this, SettingActivity.class);
            startActivity(intent);        }

        return super.onOptionsItemSelected(item);
    }

    public void ViewProfile(View v) {
        Intent intent = new Intent(InventoryItemActivity.this, ProfileActivity.class);
        ObjParseSingleton.getInstance().addObject("userProfile", ownerProfile);
        startActivity(intent);
    }


}
