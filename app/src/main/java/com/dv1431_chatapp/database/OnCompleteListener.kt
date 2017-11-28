package com.dv1431_chatapp.database

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

/**
 * Created by dane on 11/28/17.
 */

interface OnCompleteListener {
    fun onStart()
    fun onSuccess(task: Task<AuthResult>)
    fun onFail(task: Task<AuthResult>)
}