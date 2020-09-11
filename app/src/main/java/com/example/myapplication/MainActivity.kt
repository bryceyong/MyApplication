package com.example.myapplication

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.clj.fastble.BleManager
import com.clj.fastble.callback.BleScanCallback
import com.clj.fastble.data.BleDevice
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scan.setOnClickListener {

            var list = mutableListOf<BleDevice>()
            var str = ""
            checkPermissions()
            startScan(list)
            for (item in list){
                str = str + item.name + "\n"
            }
            text.setText(str)


        }




    }

    private val REQUEST_CODE_PERMISSION_LOCATION = 2

    private fun checkPermissions() {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (!bluetoothAdapter.isEnabled)
        {
            Toast.makeText(applicationContext,
                "Please turn on Bluetooth first",Toast.LENGTH_LONG).show()
            return
        }

        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val permissionDeniedList = ArrayList<String>()
        for (permission in permissions) {
            val permissionCheck = applicationContext.let { ContextCompat.checkSelfPermission(it, permission)
            }
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {

            } else {
                permissionDeniedList.add(permission)
            }
        }
        if (!permissionDeniedList.isEmpty()) {
            val deniedPermissions = permissionDeniedList.toTypedArray()

                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    deniedPermissions,
                    REQUEST_CODE_PERMISSION_LOCATION

                )

        }
    }



    private fun startScan(list: MutableList<BleDevice>){
        BleManager.getInstance().scan(object : BleScanCallback() {
            override fun onScanStarted(success: Boolean) {
                Toast.makeText(applicationContext, "Scan started", Toast.LENGTH_LONG).show()
            }

            override fun onLeScan(bleDevice: BleDevice?) {
                super.onLeScan(bleDevice)
            }

            override fun onScanning(bleDevice: BleDevice) {
                list.add(bleDevice);
                var scanResultList: List<BleDevice> = list
            }
            override fun onScanFinished(scanResultList: List<BleDevice>) {
                Toast.makeText(applicationContext, "Scan over", Toast.LENGTH_LONG).show()
            }
        })
    }
}