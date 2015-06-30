package domain

/**
 * Created by Denis on 28.06.2015.
 */
class Group {
    int id
    String title


    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
