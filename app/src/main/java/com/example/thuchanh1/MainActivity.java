package com.example.thuchanh1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterPerson.PersonItemListener, SearchView.OnQueryTextListener{
    private AdapterPerson adapter;
    private RecyclerView recyclerView;
    private RadioGroup radioGroup;
    private RadioButton female;
    private RadioButton male;
    private Button add, update;
    private EditText editName, editTimeCheckin, editDateCheckin;
    private SearchView searchView;
    private Spinner sp;
    private Integer currentPosition;

    private int[] imgs = {R.drawable.user2, R.drawable.user1, R.drawable.user3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        recycleViewSetUp();
        editDateCheckin.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int x = calendar.get(Calendar.YEAR);
            int y = calendar.get(Calendar.MONTH);
            int z = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    editDateCheckin.setText(dayOfMonth + " / " + month + " / " + year);
                }
            }, x, y, z);
            dialog.show();
        });

        editTimeCheckin.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int x = calendar.get(Calendar.HOUR);
            int y = calendar.get(Calendar.MINUTE);
            int z = calendar.get(Calendar.SECOND);
            TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    editTimeCheckin.setText(i + " / " + i1);
                }

            }, x, y, true);
            dialog.show();
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rname = editName.getText().toString();
                String rtimeCheckIn = editTimeCheckin.getText().toString();
                String rdateCheckIn = editDateCheckin.getText().toString();
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                String i = sp.getSelectedItem().toString();
                if(rname.isEmpty() || rtimeCheckIn.isEmpty() || rdateCheckIn.isEmpty() || radioButtonId < 0) {
                    Toast.makeText(MainActivity.this, "Vui Long Dien Day Du Thong Tin", Toast.LENGTH_LONG).show();
                } else {
                    System.out.println("runnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
                    RadioButton radioButton = radioGroup.findViewById(radioButtonId);
                    String gender = radioButton.getText().toString();
                    adapter.add(new Person(rname, rtimeCheckIn, rdateCheckIn, gender, imgs[Integer.parseInt(i)]));
                    Toast.makeText(MainActivity.this, "Them thanh cong", Toast.LENGTH_LONG).show();
                }
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rname = editName.getText().toString();
                String rtimeCheckIn = editTimeCheckin.getText().toString();
                String rdateCheckIn = editDateCheckin.getText().toString();
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                int img = R.drawable.user2;
                String i = sp.getSelectedItem().toString();
                if(rname.isEmpty() || rtimeCheckIn.isEmpty() || rdateCheckIn.isEmpty() || radioButtonId < 0) {
                    Toast.makeText(MainActivity.this, "Vui Long Dien Day Du Thong Tin", Toast.LENGTH_LONG).show();
                } else {
                    RadioButton radioButton = radioGroup.findViewById(radioButtonId);
                    String gender = radioButton.getText().toString();
                    adapter.update(currentPosition, new Person(rname, rtimeCheckIn, rdateCheckIn, gender,
                            imgs[Integer.parseInt(i)]));
                    Toast.makeText(MainActivity.this, "Them thanh cong", Toast.LENGTH_LONG).show();

                }
                update.setEnabled(false);
                add.setEnabled(true);
            }
        });
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        currentPosition = position;
        add.setEnabled(false);
        update.setEnabled(true);
        Person person = adapter.getItemAt(position);
        editName.setText(person.getName());
        editTimeCheckin.setText(person.getTimeCheckIn());
        editDateCheckin.setText(person.getDateCheckIn());
        if(person.getGender().equalsIgnoreCase("female")) {
            radioGroup.check(R.id.radioButtonFemale);
        } else {
            radioGroup.check(R.id.radioButtonMale);
        }
    }

    private void recycleViewSetUp() {
        List<Person> personList = new ArrayList<Person>();
        Person person1 = new Person
                ("NGUYEN HUY", "1/2/2023", "3/4/2023", "MALE", R.drawable.user2);
        Person person2 = new Person
                ("NGUYEN LINH", "1/2/2023", "3/4/2023", "FEMALE", R.drawable.user2);
        Person person3 = new Person
                ("NGUYEN TRANG", "1/2/2023", "3/4/2023", "FEMALE", R.drawable.user2);
        Person person4 = new Person
                ("NGUYEN HUNG", "1/2/2023", "3/4/2023", "MALE", R.drawable.user2);
        personList.add(person1);
        personList.add(person2);
        personList.add(person3);
        personList.add(person4);

        adapter = new AdapterPerson(this, personList);
        adapter.setPersonItemListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

    }
    public void init() {
        recyclerView = findViewById(R.id.recycleView);
        radioGroup = findViewById(R.id.radioGroup);
        female = findViewById(R.id.radioButtonFemale);
        male = findViewById(R.id.radioButtonMale);
        add = findViewById(R.id.btAdd);
        update = findViewById(R.id.btUpdate);
        editTimeCheckin = findViewById(R.id.editTime);
        editDateCheckin = findViewById(R.id.editDate);
        editName = findViewById(R.id.editName);
        searchView = findViewById(R.id.search);
        sp = findViewById(R.id.selectImg);
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this);
        sp.setAdapter(spinnerAdapter);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        filter(s.toLowerCase());
        return false;
    }
    private void filter(String s) {
        List<Person> filter = new ArrayList<>();
        for (Person person : adapter.getListBackup()) {
            if (person.getName().toLowerCase().contains(s)) {
                filter.add(person);
            }
        }
        if(filter.isEmpty()) {
            Toast.makeText(MainActivity.this, "Không tìm thấy ", Toast.LENGTH_LONG);
        } else {
            adapter.filterPerson(filter);
        }
    }
}