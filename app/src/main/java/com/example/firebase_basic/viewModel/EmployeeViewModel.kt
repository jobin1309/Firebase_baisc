//package com.example.firebase_basic.viewModel
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//
//class EmployeeViewModel: ViewModel() {
//
//    private val insertionResult: MutableLiveData<Boolean> = MutableLiveData()
//
//    fun saveEmployeeData(
//        empName: String,
//        empEmail: String,
//        empAgeString: String
//    ): LiveData<Boolean> {
//
//        // Check input validity and perform necessary validations
//
//        val empAge = try {
//            empAgeString.toInt()
//        } catch (e: NumberFormatException) {
//            insertionResult.value = false
//            return insertionResult
//        }
//
//        // Existing code
//
//        for (employee in employeesList) {
//            newEmployeeRef.setValue(employee)
//                .addOnCompleteListener {
//                    insertionResult.value = true // Indicate successful insertion
//                }
//                .addOnFailureListener { err ->
//                    insertionResult.value = false // Indicate insertion failure
//                    Toast.makeText(context, "Error: ${err.message}", Toast.LENGTH_SHORT).show()
//                }
//        }
//
//        // Existing code
//
//        return insertionResult
//    }
//}
//
//
//}