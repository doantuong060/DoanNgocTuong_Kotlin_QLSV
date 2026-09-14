import java.util.Scanner

// 1. Khai bao lop du lieu Student
data class Student(
    val id: String,
    val fullName: String,
    val age: Int,
    val major: String,
    val gpa: Double
) {
    override fun toString(): String {
        return "ID: %-8s | Name: %-20s | Age: %-3d | Major: %-15s | GPA: %.2f".format(id, fullName, age, major, gpa)
    }
}

class StudentManager {
    private val students = mutableListOf<Student>()

     init {
        students.add(Student("SV01", "Nguyen Van An", 20, "IT", 8.5))
        students.add(Student("SV02", "Tran Thi Binh", 22, "SE", 4.5))
        students.add(Student("SV03", "Le Van Cuong", 19, "IT", 7.5))
        students.add(Student("SV04", "Pham Thi Dung", 21, "Marketing", 9.2))
        students.add(Student("SV05", "Hoang Van Em", 20, "SE", 6.8))
    }

    private fun printList(list: List<Student>) {
        if (list.isEmpty()) {
            println("Danh sach trong!")
        } else {
            list.forEach { println(it) }
        }
    }

    fun addStudent() {
        val scanner = Scanner(System.`in`)
        print("Enter Student ID: ")
        val id = scanner.nextLine()
        print("Enter Full Name: ")
        val name = scanner.nextLine()
        print("Enter Age: ")
        val age = scanner.nextInt()
        scanner.nextLine() // Clear buffer
        print("Enter Major: ")
        val major = scanner.nextLine()
        print("Enter GPA: ")
        val gpa = scanner.nextDouble()

        students.add(Student(id, name, age, major, gpa))
        println("=> Them sinh vien thanh cong!")
    }

    fun displayAll() {
        println("--- DANH SACH SINH VIEN ---")
        printList(students)
    }

    // Tim sinh vien theo mot phan ten
    fun searchStudent(partialName: String) {
        val result = students.filter { it.fullName.contains(partialName, ignoreCase = true) }
        println("--- KET QUA TIM KIEM CHO '$partialName' ---")
        printList(result)
    }

    fun calculateAverageGpa() {
        if (students.isEmpty()) {
            println("Chua co sinh vien de tinh diem.")
            return
        }
        val avg = students.map { it.gpa }.average()
        println("=> Diem GPA trung binh cua toan truong: %.2f".format(avg))
    }

    fun findStudentWithHighestGpa() {
        val maxGpa = students.maxOfOrNull { it.gpa }
        if (maxGpa != null) {
            val bestStudents = students.filter { it.gpa == maxGpa }
            println("--- SINH VIEN CO GPA CAO NHAT ($maxGpa) ---")
            printList(bestStudents)
        } else {
            println("Danh sach trong!")
        }
    }

    fun removeStudent(id: String) {
        val removed = students.removeIf { it.id.equals(id, ignoreCase = true) }
        if (removed) {
            println("=> Da xoa sinh vien co ID: $id")
        } else {
            println("=> Khong tim thay sinh vien co ID: $id")
        }
    }

    fun advancedQueries(scanner: Scanner) {
        println("\n--- TIM KIEM & THONG KE NANG CAO ---")
        println("a. Dem so sinh vien co GPA >= 8.0")
        println("b. Dem so sinh vien co GPA < 5.0")
        println("c. Tinh GPA trung binh theo nganh")
        println("d. Tim sinh vien lon tuoi nhat")
        println("e. Tim sinh vien co GPA tu 7.0 den 8.5")
        println("f. Tim tat ca sinh vien thuoc mot nganh")
        print("Chon chuc nang (a-f): ")
        
        when (scanner.nextLine().trim().lowercase()) {
            "a" -> println("=> So sinh vien GPA >= 8.0: ${students.count { it.gpa >= 8.0 }}")
            "b" -> println("=> So sinh vien GPA < 5.0: ${students.count { it.gpa < 5.0 }}")
            "c" -> {
                print("Nhap ten nganh: ")
                val major = scanner.nextLine()
                val majorStudents = students.filter { it.major.equals(major, ignoreCase = true) }
                if (majorStudents.isNotEmpty()) {
                    val avg = majorStudents.map { it.gpa }.average()
                    println("=> Diem trung binh cua nganh $major la: %.2f".format(avg))
                } else {
                    println("Khong co sinh vien nao thuoc nganh $major.")
                }
            }
            "d" -> {
                val maxAge = students.maxOfOrNull { it.age }
                if (maxAge != null) {
                    println("--- SINH VIEN LON TUOI NHAT ($maxAge tuoi) ---")
                    printList(students.filter { it.age == maxAge })
                }
            }
            "e" -> {
                println("--- SINH VIEN CO GPA 7.0 -> 8.5 ---")
                printList(students.filter { it.gpa in 7.0..8.5 })
            }
            "f" -> {
                print("Nhap ten nganh can tim: ")
                val major = scanner.nextLine()
                println("--- SINH VIEN NGANH $major ---")
                printList(students.filter { it.major.equals(major, ignoreCase = true) })
            }
            else -> println("Lua chon khong hop le!")
        }
    }

    // --- CAC YEU CAU SAP XEP (NANG CAO) ---

    fun advancedSorting(scanner: Scanner) {
        println("\n--- SAP XEP & HIEN THI ---")
        println("a. Sap xep sinh vien theo GPA giam dan")
        println("b. Hien thi 3 sinh vien co GPA cao nhat")
        println("c. Sap xep sinh vien theo tuoi")
        println("d. Sap xep sinh vien theo ten")
        print("Chon chuc nang (a-d): ")
        
        when (scanner.nextLine().trim().lowercase()) {
            "a" -> {
                println("--- DANH SACH THEO GPA GIAM DAN ---")
                printList(students.sortedByDescending { it.gpa })
            }
            "b" -> {
                println("--- TOP 3 SINH VIEN GPA CAO NHAT ---")
                printList(students.sortedByDescending { it.gpa }.take(3))
            }
            "c" -> {
                println("--- DANH SACH SAP XEP THEO TUOI (TANG DAN) ---")
                printList(students.sortedBy { it.age })
            }
            "d" -> {
                println("--- DANH SACH SAP XEP THEO TEN (A-Z) ---")
                // Lay phan ten cuoi cung (ten chinh) de sap xep cho chuan tieng Viet/Anh
                printList(students.sortedBy { it.fullName.split(" ").last() })
            }
            else -> println("Lua chon khong hop le!")
        }
    }
}

fun main() {
    val manager = StudentManager()
    val scanner = Scanner(System.`in`)
    var choice: Int

    do {
        println("\n========== STUDENT MANAGEMENT ==========")
        println("1. Add student")
        println("2. Display all students")
        println("3. Search student (by Name)")
        println("4. Calculate average GPA")
        println("5. Find student with highest GPA")
        println("6. Remove student")
        println("7. [Nang cao] Tim kiem & Thong ke")
        println("8. [Nang cao] Sap xep danh sach")
        println("0. Exit")
        println("========================================")
        print("Choose: ")
        
        choice = try {
            scanner.nextInt()
        } catch (e: Exception) {
            -1 
        }
        scanner.nextLine()

        when (choice) {
            1 -> manager.addStudent()
            2 -> manager.displayAll()
            3 -> {
                print("Nhap ten sinh vien can tim: ")
                manager.searchStudent(scanner.nextLine())
            }
            4 -> manager.calculateAverageGpa()
            5 -> manager.findStudentWithHighestGpa()
            6 -> {
                print("Nhap ID sinh vien can xoa: ")
                manager.removeStudent(scanner.nextLine())
            }
            7 -> manager.advancedQueries(scanner)
            8 -> manager.advancedSorting(scanner)
            0 -> println("Dang thoat chuong trinh. Tam biet!")
            else -> println("Lua chon khong hop le, vui long nhap so tu 0-8.")
        }
    } while (choice != 0)
}