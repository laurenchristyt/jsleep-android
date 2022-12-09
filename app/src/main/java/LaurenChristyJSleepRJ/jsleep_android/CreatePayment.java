package LaurenChristyJSleepRJ.jsleep_android;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

import LaurenChristyJSleepRJ.jsleep_android.model.Payment;
import LaurenChristyJSleepRJ.jsleep_android.request.BaseApiService;
import LaurenChristyJSleepRJ.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreatePayment extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    EditText dateFrom,dateTo,monthFrom,monthTo,yearFrom,yearTo;
    Button proceedPayment, cancelPayment,acceptPayment,rejectPayment,seeVoucher,backtoRoomDetail;
    public static Payment bill;
    //public static int idroom;
    public static boolean accepted,rejected;
    public static boolean isUseVoucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_create);

        mApiService = UtilsApi.getApiService();
        mContext = this;

        dateFrom=findViewById(R.id.inputDateFrom);
        monthFrom=findViewById(R.id.inputMonthFrom);
        yearFrom=findViewById(R.id.inputYearFrom);
        dateTo=findViewById(R.id.inputDateTo);
        monthTo=findViewById(R.id.inputMonthTo);
        yearTo=findViewById(R.id.inputYearTo);
//create payment gak ngurangin balance
        //jadi kek bikin tagihan doang
        proceedPayment = findViewById(R.id.confirmPaymentButton);
        //cancelPayment = findViewById(R.id.cancelPaymentButton);
        acceptPayment = findViewById(R.id.acceptPayment);
        rejectPayment = findViewById(R.id.rejectPayment);
        backtoRoomDetail=findViewById(R.id.backtoMainbutton);
        seeVoucher = findViewById(R.id.useVoucher);
//        if(RoomDetail.currentRoom.booked==null){
//            proceedPayment.setVisibility(View.VISIBLE);
//        }
//        else{
//            proceedPayment.setVisibility(View.INVISIBLE);
//        }

//        cancelPayment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //RoomDetail.currentRoom=null;
//                startActivity(new Intent(CreatePayment.this, RoomDetail.class));
//            }
//        });

        backtoRoomDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreatePayment.this, DetailRoomActivity.class));
            }
        });

        proceedPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //proceedPayment.setVisibility(View.INVISIBLE);
                String strt = dateFrom.getText().toString()+"/"+monthFrom.getText().toString()+"/"+yearFrom.getText().toString();
                System.out.println(dateFrom.getText().toString());
                String end = dateTo.getText().toString()+"/"+monthTo.getText().toString()+"/"+yearTo.getText().toString();
                System.out.println(strt+"  "+end);
                Payment none = requestPayment(strt.toString(),end.toString());
                //potensi bug disini, karena blok if mungkin dieksekusi sebelum response muncul

            }
        });

        //acceptPayment.setOnClickListener, method accept panggil disini,rejectPayment.setVisibility(VISIBLE)
        acceptPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean rtn = acceptPayment();
                //RoomDetail.roomBill=bill;
                acceptPayment.setVisibility(View.INVISIBLE);
                seeVoucher.setVisibility(View.INVISIBLE);
                rejectPayment.setVisibility(View.VISIBLE);
            }
        });
        rejectPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean rtn = refundPayment();
                //RoomDetail.roomBill=null;
                acceptPayment.setVisibility(View.VISIBLE);
                rejectPayment.setVisibility(View.INVISIBLE);
            }
        });

//        seeVoucher.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //isUseVoucher = true;
//                startActivity(new Intent(CreatePayment.this,VoucherList.class));
//            }
//        });
    }

    protected Payment requestPayment(String from, String to){
        System.out.println(from+"  "+to+" from requestPayment");
        System.out.println("Account Info: "+LoginActivity.accountLogin.toString());
        System.out.println("Room Info: "+ DetailRoomActivity.currentRoom.toString());
        mApiService.createPayment(LoginActivity.accountLogin.id,LoginActivity.accountLogin.renter.id,
                DetailRoomActivity.currentRoom.id,from,to).enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if(response.isSuccessful()){
                    bill = response.body();
                    System.out.println("Resp: "+bill.toString());
                    if(bill!=null){
                        Toast.makeText(mContext, "Create Bill Success!", Toast.LENGTH_LONG).show();
                        proceedPayment.setVisibility(View.INVISIBLE);
                        //cancelPayment.setVisibility(View.INVISIBLE);
                        acceptPayment.setVisibility(View.VISIBLE);
                        seeVoucher.setVisibility(View.VISIBLE);
                        rejectPayment.setVisibility(View.INVISIBLE);
                    }
                    //bikin activity ke RoomDetail yg ada billnya?
                    Toast.makeText(mContext, "Create Bill Success!", Toast.LENGTH_LONG).show();

                }
                else{
                    bill = response.body();
//                    System.out.println("Resp: "+bill.toString());
                    Toast.makeText(mContext, "Cek Payment Response", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext, "Create Bill Failed!,Check again your inputs and balance", Toast.LENGTH_LONG).show();
            }
        });
        return null;
    }

    protected Boolean acceptPayment(){
        mApiService.accept(bill.id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    accepted = response.body();
                    System.out.println("Resp: "+bill.toString());
                    if(accepted){
//                        if(VoucherDetail.selectedVoucher!=null){
//ini buat apa ya?
//                        }
                        Toast.makeText(mContext, "Payment Successful!", Toast.LENGTH_LONG).show();
                    }

                    else{
                        Toast.makeText(mContext, "Room already booked!", Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(mContext, "Payment Failed", Toast.LENGTH_LONG).show();
            }
        });
        return false;
    }

    protected Boolean refundPayment(){
        mApiService.cancel(bill.id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    rejected = response.body();
                    System.out.println("Resp: "+bill.toString());
                    if(rejected){
                        //operasi metematika refund disini apa di aboutMe ya?
                        //requestTopUp(-1*(hargakamar-cutPrice);
//                        if(isUseVoucher){
//
//                        }
                        Toast.makeText(mContext, "Refund successful, check your balance!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(mContext, "Refund failed", Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(mContext, "Backend error", Toast.LENGTH_LONG).show();
            }
        });
        return false;
    }

}