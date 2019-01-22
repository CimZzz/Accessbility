package test.test.test.accessbility

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import kotlinx.android.synthetic.main.activity_main.*
import test.test.test.tools.DataModule
import test.test.test.tools.ToastUtils


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openBtn.setOnClickListener {
            finish()
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }


        inputTxt.setText(DataModule.getAutoInputTxt()?:"")

        setInput.setOnClickListener {
            DataModule.setAutoInputTxt(inputTxt.text.toString())
            ToastUtils.sendToast("设置成功")
        }

        openInput.isSelected = DataModule.isOpenAutoInput()
        if(openInput.isSelected)
            openInput.text = "关闭自动发送"
        else openInput.text = "打开自动发送"
        openInput.setOnClickListener {
            if(openInput.isSelected) {
                openInput.isSelected = false
                DataModule.setAutoInput(false)
                openInput.text = "打开自动发送"
            }
            else {
                openInput.isSelected = true
                DataModule.setAutoInput(true)
                openInput.text = "关闭自动发送"
            }
        }
    }
}
