package com.example.thuchanh1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterPerson extends RecyclerView.Adapter<AdapterPerson.PersionViewHolder>{
    private List<Person> personList;
    private List<Person> listBackup;
    private Context context;
    private PersonItemListener personItemListener;

    public List<Person> getPersonList() {
        return personList;
    }

    public AdapterPerson(Context context, List<Person> personList) {
        this.personList = new ArrayList<>(personList);
        this.listBackup = new ArrayList<>(personList);
        this.context = context;
    }


    public List<Person> getListBackup() {
        return listBackup;
    }
    public void filterPerson(List<Person> filterList){
        personList = new ArrayList<>(filterList);
        notifyDataSetChanged();
    }

    public void add(Person person) {
        personList.add(person);
        listBackup.add(person);
        notifyDataSetChanged();
    }

    public void update(int rListPosition, Person person) {
        listBackup.set(rListPosition, person);
        personList.set(rListPosition,person);
        notifyDataSetChanged();
    }

    public Person getItemAt(int position) {
        return personList.get(position);
    }
    public void setPersonItemListener(PersonItemListener personItemListener) {
        this.personItemListener = personItemListener;
    }

    @NonNull
    @Override
    public PersionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new PersionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersionViewHolder holder, int position) {
        Person person = personList.get(position);
        if(person == null) {
            return;
        }
        holder.name.setText(person.getName());
        holder.checkInTime.setText(person.getTimeCheckIn());
        holder.checkInDate.setText(person.getDateCheckIn());
        holder.imageView.setImageResource(person.getImg());
        holder.gender.setText(person.getGender());
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public class PersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name, checkInTime, checkInDate, gender;
        private ImageView imageView;
        private Button remove;
        public PersionViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageviewimg);
            name = itemView.findViewById(R.id.tvname);
            checkInTime = itemView.findViewById(R.id.tvtimecheckin);
            checkInDate = itemView.findViewById(R.id.tvdatecheckin);
            gender = itemView.findViewById(R.id.tvgender);
            remove = itemView.findViewById(R.id.btremove);
            itemView.setOnClickListener(this);

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Thông báo xóa");
                    builder.setMessage("Bạn có chắc muốn xóa " + personList.get(getAdapterPosition()).getName() + " này không");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            personList.remove(getAdapterPosition());
                            listBackup.remove(getAdapterPosition());
                            Toast.makeText(context.getApplicationContext(), "Xoa Thanh Cong", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    builder.show();
//                    personList.remove(getAdapterPosition());
//                    listBackup.remove(getAdapterPosition());
//                    Toast.makeText(context.getApplicationContext(), "Xoa Thanh Cong", Toast.LENGTH_SHORT).show();
//                    notifyDataSetChanged();
                }
            });

        }
        @Override
        public void onClick(View view) {
            if(personItemListener == null) return;
            personItemListener.onItemClick(view, getAdapterPosition());
        }
    }
    public interface PersonItemListener{
        void onItemClick(View view, int position);
    }
}
