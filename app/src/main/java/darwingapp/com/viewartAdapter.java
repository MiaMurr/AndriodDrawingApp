package darwingapp.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class viewartAdapter extends RecyclerView.Adapter<viewartAdapter.ViewHolder> {

    private ArrayList<String> imageList;
    private ArrayList<ImageViewModel> imgname;

    public viewartAdapter(ArrayList<String> imageList, ArrayList<ImageViewModel> imgname, Context context) {
        this.imageList = imageList;
        this.context = context;
        this.imgname = imgname;
    }

    private Context context;

    @NonNull
    @Override
    public viewartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewdrawings, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewartAdapter.ViewHolder holder, int position) {
        // loading the images from the position
        Glide.with(holder.itemView.getContext()).load(imageList.get(position)).into(holder.imageView);
        holder.textView.setText(imgname.get(position).getImagename());
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item);
            textView = itemView.findViewById(R.id.NameofDrawing);
        }
    }
}

    /*private Context mContext;
    private List<ImageViewModel> mUploads;

    public viewartAdapter(Context context, List<ImageViewModel> uploads){

        mContext = Context
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView textviewName;
        public ImageView imageView;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            textviewName = itemView.findViewById(R.id.NameOfDrawing);
            imageView = itemView.findViewById(R.id.UsersDrawing);
        }
    }

    private java.util.List<ImageViewModel> List;

    private StorageReference ref;
// other code
// goes here...


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageView ImageView;
        public MyViewHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.NameOfDrawing);
            ImageView = (ImageView) view.findViewById(R.id.UsersDrawing);
        }
    } // end MyViewHolder

    public viewartAdapter(java.util.List<ImageViewModel> List) {
        this.List = List;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewdrawings, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {



        ImageViewModel name = List.get(position);

        ref = FirebaseStorage.getInstance().getReference().child(name.getName() + ".jpeg");
        File temp;
        try {
            temp = File.createTempFile(name.getName() + ".jpeg","jpeg");

            ref.getFile(temp).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap img = BitmapFactory.decodeFile(temp.getAbsolutePath());
                    holder.ImageView.setImageBitmap(Bitmap.createScaledBitmap(img,200,200,false));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.textViewName.setText(name.getName());


    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public void setData(List<ImageViewModel> List) {
        this.List = List;
        notifyDataSetChanged();
    }
}*/

/*public class viewartAdapter extends RecyclerView.Adapter<viewartAdapter.ImageViewHolder>{

    StorageReference ref;

    private int[] images;

    public viewartAdapter(int[] images){

    this.images = images;

    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewdrawings,parent,false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);

        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

        ref = FirebaseStorage.getInstance().getReference().child(ImageViewModel.);

        StorageReference pathReference = ref.child("images/stars.jpg");

// Create a reference to a file from a Cloud Storage URI
        StorageReference gsReference = storage.getReferenceFromUrl("gs://bucket/images/stars.jpg");


        /*int image_id = images[position];
        holder.image.setImageResource(image_id);
        holder.name.setText("Name: " + position);

    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.UsersDrawing);
            name = itemView.findViewById(R.id.NameOfDrawing);
        }
    }*/

   /* private final Context context;
    private List<ImageViewModel> ListOfDrawings = new ArrayList<>();

    public viewartAdapter(Context context, List<ImageViewModel> ListOfDrawings){

        this.context=context;
        this.ListOfDrawings=ListOfDrawings;

        
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public MyViewHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.NameOfDrawing);
        }
    } // end MyViewHolder

    public NameAdapter(List<String> drawingList) {
        this.drawingList = drawingList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewdrawings, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String name = ListOfDrawings.get(position);
        holder.textViewName.setText(name);
    }

    @Override
    public int getItemCount() {
        return ListOfDrawings.size();
    }

    public void setData(List<String> nameList) {
        this.ListOfDrawings = ListOfDrawings;
        notifyDataSetChanged();
    }*/


//}
