package com.devparadigam.agrade.ui.activiries;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.devparadigam.agrade.ApplicationParentClass;
import com.devparadigam.agrade.R;
import com.devparadigam.agrade.base.BaseActivity;
import com.devparadigam.agrade.databinding.PaymentBinding;
import com.devparadigam.agrade.model.factories.UserFactory;
import com.devparadigam.agrade.model.repositories.UserRepository;
import com.devparadigam.agrade.model.requests.Resource;
import com.devparadigam.agrade.model.response.TransactionModel;
import com.devparadigam.agrade.model.response.UserProfileDetails;
import com.devparadigam.agrade.ui.fragments.HomeFragment;
import com.devparadigam.agrade.utils.AppEnvironment;
import com.devparadigam.agrade.utils.StaticData;
import com.devparadigam.agrade.viewmodels.UserViewModel;
import com.google.gson.Gson;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.core.response.PayumoneyResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;


import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class PaymentActivity extends BaseActivity {

    PaymentBinding binding;
    private HashMap<String, String> params = new HashMap<>();
    String price, productName, mobile, email, token, exam_id,paperId;
    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;
    UserViewModel viewModel;
    View view;
    TransactionModel transactionModel;
    HomeFragment homeFragment;
    Bundle bundle;
    boolean isPaymentSuccessful = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeFragment = new HomeFragment();
        bundle = new Bundle();
        binding = DataBindingUtil.setContentView(this, R.layout.payment_activity);
        token = StaticData.TOKEN_TYPE+" "+mPref.getUserProfileDetails(PaymentActivity.this).getToken();
        exam_id = getIntent().getStringExtra("exam_id");
        paperId = getIntent().getStringExtra("paperId");
        setUpViewModel();

        price = getIntent().getStringExtra("price");
        productName = getIntent().getStringExtra("productName");
        binding.price.setText(price);
        ;
        binding.testname.setText(productName);
        observerGetProfile(token);


        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mobile = binding.editTextmobile.getText().toString();
                email = binding.emailtext.getText().toString();

                if (validate()) {
                    launchPayUMoneyFlow();
                }

            }
        });


        // String hashSequence = key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5||||||salt;
        // String serverCalculatedHash= hashCal("SHA-512", hashSequence);

        selectSandBoxEnv();


    }

    boolean validate() {
        if (!mobile.matches("[0-9]{10}")) {
            binding.editTextmobile.setError("please check the Mobile no.");
            binding.editTextmobile.requestFocus();
            return false;
        }
        if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
            binding.emailtext.requestFocus();
            binding.emailtext.setError("Please check your Email Address");
            return false;
        }
        return true;
    }

    private void launchPayUMoneyFlow() {

        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();

        double amount = 0;
        try {
            amount = Double.parseDouble(price); //Double.valueOf(price);

        } catch (Exception e) {
            e.printStackTrace();
        }
        String txnId = System.currentTimeMillis() + "";
        //String txnId = "TXNID720431525261327973";
        String phone = mobile;
        String productName = "" + this.productName;
        String firstName = "" + mPref.getUserProfileDetails(this).getName();
        String email1 = email;
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";
        String udf6 = "";
        String udf7 = "";
        String udf8 = "";
        String udf9 = "";
        String udf10 = "";

        AppEnvironment appEnvironment = ((ApplicationParentClass) getApplication()).getAppEnvironment();
        builder.setAmount(String.valueOf(amount))
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email1)
                .setsUrl(appEnvironment.surl())
                .setfUrl(appEnvironment.furl())
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setUdf6(udf6)
                .setUdf7(udf7)
                .setUdf8(udf8)
                .setUdf9(udf9)
                .setUdf10(udf10)
                .setIsDebug(appEnvironment.debug())
                .setKey(appEnvironment.merchant_Key())
                .setMerchantId(appEnvironment.merchant_ID());

        try {
            mPaymentParams = builder.build();


            mPaymentParams = calculateServerSideHashAndInitiatePayment1(mPaymentParams);

            PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, PaymentActivity.this, R.style.AppTheme_default, false);

        } catch (Exception e) {
            // some exception occurred
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

    private PayUmoneySdkInitializer.PaymentParam calculateServerSideHashAndInitiatePayment1(final PayUmoneySdkInitializer.PaymentParam paymentParam) {

        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, String> params = paymentParam.getParams();
        stringBuilder.append(params.get(PayUmoneyConstants.KEY) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.TXNID) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.AMOUNT) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.PRODUCT_INFO) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.FIRSTNAME) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.EMAIL) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF1) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF2) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF3) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF4) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF5) + "||||||");

        AppEnvironment appEnvironment = ((ApplicationParentClass) getApplication()).getAppEnvironment();
        stringBuilder.append(appEnvironment.salt());

        String hash = hashCal(stringBuilder.toString());
        paymentParam.setMerchantHash(hash);

        return paymentParam;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result Code is -1 send from Payumoney activity
        Log.d("MainActivity", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data !=
                null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                    .INTENT_EXTRA_TRANSACTION_RESPONSE);

            ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);

            // Check which object is non-null
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    //Success Transaction
                    String payuResponse = transactionResponse.getPayuResponse();
                    Gson gson = new Gson();
                    PayumoneyResponse response = gson.fromJson(payuResponse, PayumoneyResponse.class);
                    try {
                        JSONObject jsonObject=new JSONObject(payuResponse);
                      String txnid=  jsonObject.getJSONObject("result").getString("txnid");
                        observerGetTransaction(token, price, exam_id, txnid, payuResponse);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //Failure Transaction
                    finish();
                }

                // Response from Payumoney
                String payuResponse = transactionResponse.getPayuResponse();

                // Response from SURl and FURL
                String merchantResponse = transactionResponse.getTransactionDetails();

                new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setMessage("Payu's Data : " + payuResponse + "\n\n\n Merchant's Data: " + merchantResponse)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }).show();

            } else if (resultModel != null && resultModel.getError() != null) {
                Log.d("PaymentActivity", "Error response : " + resultModel.getError().getTransactionResponse());
                Toast.makeText(this, "Error response : " + resultModel.getError().getTransactionResponse(), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Log.d("PaymentActivity", "Both objects are null!");
                Toast.makeText(this, "Both objects are null!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    private void selectSandBoxEnv() {
        ((ApplicationParentClass) getApplication()).setAppEnvironment(AppEnvironment.PRODUCTION);
    }

    public static String hashCal(String str) {
        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (byte aMessageDigest : messageDigest) {
                String hex = Integer.toHexString(0xFF & aMessageDigest);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }
        return hexString.toString();
    }


    private void setUpViewModel() {
        UserFactory factory = new UserFactory(new UserRepository(ApplicationParentClass.getmInstance().getApiServices()));
        try {
            viewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void observerGetTransaction(String token, String amount, String examId, String transactionId, String response) {
        viewModel.getTransaction(token, amount, examId, transactionId, response).observe(this, new Observer<Resource<TransactionModel>>() {
            @Override
            public void onChanged(Resource<TransactionModel> transactionResource) {
                switch (transactionResource.status) {
                    case IDEAL: {
                    }
                    break;
                    case LOADING: {
                        enableLoadingBar(true);
                    }
                    break;
                    case ERROR: {
                        enableLoadingBar(false);
                        if (transactionResource.message != null)
                            showSnackBar(view, transactionResource.message);
                        finish();
                    }
                    break;
                    case SUCCESS: {
                        enableLoadingBar(false);

                        transactionModel = transactionResource.data;
                        isPaymentSuccessful = true;
                        Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                        intent.putExtra("payment", isPaymentSuccessful);
                        intent.putExtra("paperId", paperId);
                        setResult(RESULT_OK, intent);
                        finish();
                        showToast(transactionResource.message);

                    }

                }
            }
        });

    }


    void observerGetProfile(String token) {
        viewModel.getProfile(token).observe(this, new Observer<Resource<UserProfileDetails>>() {
            @Override
            public void onChanged(Resource<UserProfileDetails> userDetailsResource) {
                switch (userDetailsResource.status) {
                    case IDEAL: {
                    }
                    break;
                    case LOADING: {
                        enableLoadingBar(true);
                    }
                    break;
                    case ERROR: {
                        enableLoadingBar(false);
                        if (userDetailsResource.message != null)
                            showSnackBar(binding.getRoot(), userDetailsResource.message);
                    }
                    break;
                    case SUCCESS: {
                        enableLoadingBar(false);
                        if (userDetailsResource.message != null)
                            showToast(userDetailsResource.message);

                        if (userDetailsResource.data != null) {


                            UserProfileDetails userProfileDetails = userDetailsResource.data;
                            binding.setUserInfo(userProfileDetails);

                            if (!TextUtils.isEmpty(userProfileDetails.getMobileNo())) {
                                binding.editTextmobile.setEnabled(false);
                            } else {
                                binding.editTextmobile.setEnabled(true);
                            }

                            if (!TextUtils.isEmpty(userProfileDetails.getEmail())) {
                                binding.emailtext.setEnabled(false);
                            } else {
                                binding.emailtext.setEnabled(true);
                            }

                            Toast.makeText(PaymentActivity.this, userDetailsResource.message, Toast.LENGTH_SHORT).show();


                        } else {
                            showSnackBar(binding.getRoot(), userDetailsResource.message);
                        }

                    }
                }
            }
        });

    }

}


