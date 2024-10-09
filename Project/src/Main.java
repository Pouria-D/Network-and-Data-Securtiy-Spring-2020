import java.util.Scanner;

class SparseMatrixElement{
    public double value;
    public int row;
    public int column;
    public SparseMatrixElement nextElement;


    public SparseMatrixElement(double value, int row, int column) {
        this.value = value;
        this.row = row;
        this.column = column;
    }

    public SparseMatrixElement(double value, int row, int column, SparseMatrixElement nextElement) {
        this (value, row, column);
        this.nextElement = nextElement;
    }


    public Order findOrderOf(SparseMatrixElement element){
        if (element.row>this.row) return Order.AFTER;
        else if(element.row<this.row) return Order.BEFORE;
        else{
            if (element.column>this.column) return Order.AFTER;
            else if (element.column<this.column) return Order.BEFORE;
        }
        return Order.HERE;
    }

    @Override
    public String toString() {
        return "row=" + row +
                ", column=" + column +
                ", value=" + value ;
    }

    enum Order{
        HERE,
        AFTER,
        BEFORE;
    }
}

class SparseMatrix {
    public SparseMatrixElement head;
    private int size;
    private int numOfRows;
    private int numOfColumns;

    public SparseMatrix() {
        size = 0;
        numOfRows = -1;
        numOfColumns = -1;
    }

    public void addElement(int row, int column, double value) {
        this.size++;
        if (head == null) {
            head = new SparseMatrixElement(value, row, column, null);
            updateNumOfRowCol(row, column);
        } else {
            SparseMatrixElement addedElement = new SparseMatrixElement(value, row, column);
            SparseMatrixElement thisElement = head;
            SparseMatrixElement previousElement = null;
            while (true) {
                if (thisElement.findOrderOf(addedElement) == SparseMatrixElement.Order.HERE) {
                    this.size--;
                    thisElement.value = thisElement.value + addedElement.value;
                    break;
                } else if (thisElement.findOrderOf(addedElement) == SparseMatrixElement.Order.BEFORE) {
                    updateNumOfRowCol(row, column);
                    if (previousElement == null) {
                        addedElement.nextElement = this.head;
                        this.head = addedElement;
                    } else {
                        previousElement.nextElement = addedElement;
                        addedElement.nextElement = thisElement;
                    }
                    break;
                } else {
                    if (thisElement.nextElement == null) {
                        thisElement.nextElement = addedElement;
                        updateNumOfRowCol(row, column);
                        break;
                    } else {
                        previousElement = thisElement;
                        thisElement = thisElement.nextElement;
                    }
                }
            }
        }

    }
    public void updateNumOfRowCol(int row, int column){
        if ((row+1)>this.numOfRows) this.numOfRows = row+1;
        if ((column+1)>this.numOfColumns) this.numOfColumns = column+1;
    }


    public SparseMatrixElement get(int index) {
        if (isNull()) return null;
        else{
            if (index==0) return this.head;
            else{
                int count = 0;
                SparseMatrixElement thisElement = head;
                while (thisElement.nextElement != null) {
                    thisElement = thisElement.nextElement;
                    count++;
                    if (index==count) return thisElement;
                }
            }
        }
        return null;
    }


    public SparseMatrix getRow(int index){
        if (isNull()) return null;
        else{
            SparseMatrix output = new SparseMatrix();
            SparseMatrixElement thisElement = head;
            if (thisElement.row==index) output.addElement(thisElement.row, thisElement.column,thisElement.value);
            while (thisElement.nextElement!=null) {
                thisElement = thisElement.nextElement;
                if (thisElement.row == index) output.addElement(thisElement.row, thisElement.column, thisElement.value);
                else if (thisElement.row > index) break;
            }
            return output;
        }
    }

    public int getSize() {
        if (isNull()) return 0;
        else{
            return this.size;
        }
    }

    public boolean isNull() {
        return this.head==null;
    }

    @Override
    public String toString() {
        if (head==null) return "Empty Matrix";
        else{
            String output = head + "\n";
            SparseMatrixElement thisElement = head;
            while (thisElement.nextElement!=null){
                output = output + thisElement.nextElement + "\n";
                thisElement = thisElement.nextElement;
            }
            return output;
        }
    }


    public static SparseMatrix multiply2(SparseMatrix mat1, SparseMatrix mat2){
        if (mat1.isNull() | mat2.isNull()) return null;
        else {
            SparseMatrix outputMatrix = new SparseMatrix();
            SparseMatrixElement multiplier = mat1.head;
            while (multiplier!=null){
                SparseMatrixElement multiplicand = mat2.head;
                while (multiplicand!=null){
                    if (multiplier.column==multiplicand.row) outputMatrix.addElement(multiplier.row,multiplicand.column,multiplier.value*multiplicand.value);
                    multiplicand = multiplicand.nextElement;
                }
                multiplier = multiplier.nextElement;
            }
            return outputMatrix;
        }
    }
}
class SocialMedia {

    private MyList<String> topics, persons;
    private SparseMatrix favoriteTopics, favoritePersons;
    private SparseMatrix favoritePersons2, favoritePersons3;

    public SocialMedia(MyList<String> topics, MyList<String> persons, SparseMatrix favoriteTopics, SparseMatrix favoritePersons) {
        this.topics = topics;
        this.persons = persons;
        this.favoriteTopics = favoriteTopics;
        this.favoritePersons = favoritePersons;
    }


    public SparseMatrix getFavoritePersons2() {
        if (favoritePersons2==null) {
            favoritePersons2 = SparseMatrix.multiply2(favoritePersons,favoritePersons);
            return favoritePersons2;
        } else return favoritePersons2;
    }

    public SparseMatrix getFavoritePersons3() {
        if (favoritePersons3==null) {
            favoritePersons3 = SparseMatrix.multiply2(favoritePersons,favoritePersons2);
            return favoritePersons3;
        } else return favoritePersons3;

    }

    public MyList<String> getTopics() {
        return topics;
    }

    public MyList<String> getPersons() {
        return persons;
    }

    public SparseMatrix getFavoriteTopics() {
        return favoriteTopics;
    }

    public SparseMatrix getFavoritePersons() {
        return favoritePersons;
    }

    @Override
    public String toString() {
        return "SocialMedia{" +
                "\ntopics=\n" + topics +
                "\n, persons=\n" + persons +
                "\n, favoriteTopics=\n" + favoriteTopics +
                "\n, favoritePersons=\n" + favoritePersons +
                '}';
    }

    public void merge(SocialMedia toMergeSocialMedia){
        MyList<String> oldTopicList = toMergeSocialMedia.topics;
        this.topics.merge(oldTopicList);
        MyList<String> newTopics = this.topics;

        MyList<String> oldPersonList = toMergeSocialMedia.persons;
        this.persons.merge(oldPersonList);
        MyList<String> newPersons = this.persons;
        SparseMatrix oldfavoriteTopics = toMergeSocialMedia.favoriteTopics;
        SparseMatrix oldfavoritePersons = toMergeSocialMedia.favoritePersons;

        int newRow, newColumn, oldRow, oldColumn;
        double value;
        SparseMatrixElement thisMatrixElement;
        thisMatrixElement = oldfavoriteTopics.head;
        while (thisMatrixElement!=null){
            oldRow = thisMatrixElement.row;
            oldColumn = thisMatrixElement.column;
            value = thisMatrixElement.value;
            newRow = newPersons.getIndex(oldPersonList.get(oldRow).element);
            newColumn = newTopics.getIndex(oldTopicList.get(oldColumn).element);
            this.favoriteTopics.addElement(newRow,newColumn,value);
            thisMatrixElement = thisMatrixElement.nextElement;
        }

        thisMatrixElement = oldfavoritePersons.head;
        while (thisMatrixElement!=null){
            oldRow = thisMatrixElement.row;
            oldColumn = thisMatrixElement.column;
            value = thisMatrixElement.value;
            newRow = newPersons.getIndex(oldPersonList.get(oldRow).element);
            newColumn = newPersons.getIndex(oldPersonList.get(oldColumn).element);
            this.favoritePersons.addElement(newRow,newColumn,value);
            thisMatrixElement = thisMatrixElement.nextElement;
        }

    }


    public static SocialMedia mergeAll(MyList<SocialMedia> socialMediaList){
        for (int i = 1; i <socialMediaList.getSize() ; i++) {
            socialMediaList.get(0).element.merge(socialMediaList.get(i).element);
        }
        return socialMediaList.get(0).element;
    }


}
class Parser {
    private Scanner scanner;

    public Parser(){
        scanner = new Scanner(System.in);
    }
    public MyList<SocialMedia> parseInput(){
        MyList<SocialMedia> socialMediaMyList = new MyList<>();
        int numOfSocialNetworks = Integer.parseInt(scanner.nextLine());
        MyList<String> topics, favoriteTopicsLine,favoritePersonsLine;
        MyList<Double> favoriteTopicPair,favoritePersonPair;
        int numOfFavouriteTopicsLines, numOfFavouriteTopics, numOfFavoritePersonsLine,thisPerson,numOfFavoritePersons;
        for (int socialNetworkNum = 0; socialNetworkNum < numOfSocialNetworks; socialNetworkNum++) {
            MyList<String> persons = new MyList<>();
            SparseMatrix favoriteTopics = new SparseMatrix();
            SparseMatrix favoritePersons = new SparseMatrix();
            topics = MyString.split(scanner.nextLine(),' ');
            topics.remove(0);
            numOfFavouriteTopicsLines = Integer.parseInt(scanner.nextLine());
            for (int lineNum = 0; lineNum < numOfFavouriteTopicsLines; lineNum++) {
                favoriteTopicsLine = MyString.split(scanner.nextLine(),' ');
                persons.add(String.valueOf(favoriteTopicsLine.get(0)));
                numOfFavouriteTopics = Integer.parseInt(String.valueOf(favoriteTopicsLine.get(1)));
                for (int favNum = 0; favNum < numOfFavouriteTopics; favNum++) {
                    favoriteTopicPair = MyString.splitByColon(String.valueOf(favoriteTopicsLine.get(favNum+2)));
                    favoriteTopics.addElement(lineNum, (int) Double.parseDouble(String.valueOf(favoriteTopicPair.get(0))), Double.valueOf(String.valueOf(favoriteTopicPair.get(1))));
                }
            }
            numOfFavoritePersonsLine = Integer.parseInt(scanner.nextLine());
            for (int lineNum = 0; lineNum <numOfFavoritePersonsLine ; lineNum++) {
                favoritePersonsLine = MyString.split(scanner.nextLine(),' ');
                thisPerson = Integer.parseInt(String.valueOf(favoritePersonsLine.get(0)));
                numOfFavoritePersons = Integer.parseInt(String.valueOf(favoritePersonsLine.get(1)));
                for (int favouritePersonNum = 0; favouritePersonNum < numOfFavoritePersons; favouritePersonNum++) {
                    favoritePersonPair = MyString.splitByColon(String.valueOf(favoritePersonsLine.get(favouritePersonNum+2)));
                    favoritePersons.addElement(thisPerson, (int) Double.parseDouble(String.valueOf(favoritePersonPair.get(0))), Double.valueOf(String.valueOf(favoritePersonPair.get(1))));
                }
            }
            socialMediaMyList.add(new SocialMedia(topics,persons,favoriteTopics,favoritePersons));
        }
        return socialMediaMyList;
    }

    public void parseCommands(SocialMedia socialMedia){
        int numOfTopics, depth;
        int numOfCommands = Integer.parseInt(scanner.nextLine());
        MyList<String> commands;
        MyList<MyList<String>> passedCommands = new MyList<>();
        MyList<Integer> passedDepths = new MyList<>();
        for (int i = 0; i < numOfCommands; i++) {
            commands = MyString.split(scanner.nextLine(),' ');
            numOfTopics = Integer.parseInt(commands.get(0).element);
            commands.remove(0);
            depth = Integer.parseInt(commands.get(numOfTopics).element);
            commands.remove(numOfTopics);
            passedCommands.add(commands);
            passedDepths.add(depth);
        }
        for (int i = 0; i < numOfCommands; i++) {
            Algorithm algorithm =new Algorithm(passedCommands.get(i).element,passedDepths.get(i).element,socialMedia);
            algorithm.run();
        }
    }

}


class MyString {

    public static MyList<String> split(String inputString, Character character){
        String stringToAdd = "";
        MyList<String> outputStringList = new MyList<>();
        for (int i = 0; i <inputString.length() ; i++) {
            if (inputString.charAt(i)==character ) {
                outputStringList.add(stringToAdd);
                stringToAdd = "";
            }
            else if (i==inputString.length()-1){
                stringToAdd = stringToAdd + inputString.charAt(i);
                outputStringList.add(stringToAdd);
                stringToAdd = "";
            }
            else{
                stringToAdd = stringToAdd + inputString.charAt(i);
            }
        }
        return outputStringList;
    }

    public static MyList<Double> splitByColon(String inputString){
        String stringToAdd = "";
        MyList<Double> outputStringList = new MyList<>();
        for (int i = 0; i <inputString.length() ; i++) {
            if (inputString.charAt(i)==':' ) {
                outputStringList.add(Double.parseDouble(stringToAdd));
                stringToAdd = "";
            }
            else if (i==inputString.length()-1){
                stringToAdd = stringToAdd + inputString.charAt(i);
                outputStringList.add(Double.parseDouble(stringToAdd));
                stringToAdd = "";
            }
            else{
                stringToAdd = stringToAdd + inputString.charAt(i);
            }
        }
        return outputStringList;
    }
}
class MyListElement<T> {
    public T element;
    public MyListElement nextElement;
    public MyListElement(T element){
        this.element = element;
    }

    @Override
    public String toString() {
        return element.toString();
    }

}
class MyList<T> implements MyCollection<T> {

    public MyListElement<T> head;
    private int size;

    public MyList(){
        size=0;
    }

    @Override
    public void add(T addedElement) {
        MyListElement<T> newElement = new MyListElement<>(addedElement);
        if (isNull()) head = newElement;
        else {
            MyListElement<T> thisElement = head;
            while (thisElement.nextElement != null) {
                thisElement = thisElement.nextElement;
            }
            thisElement.nextElement = newElement;
        }
        this.size++;
    }

    @Override
    public int remove(int index) {
        int size = getSize();
        if (size==0) return -1;
        else {
            this.size--;
            if (index == 0) {
                head = head.nextElement;
                return +1;
            } else if (index == size-1){
                get(size-2).nextElement = null;
                return +2;
            } else{
                get(index-1).nextElement = get(index+1);
                return +3;
            }
        }
    }


    @Override
    public MyListElement<T> get(int index) {
        if (isNull()) return null;
        else{
            if (index==0) return this.head;
            else{
                int count = 0;
                MyListElement<T> thisElement = head;
                while (thisElement.nextElement != null) {
                    thisElement = thisElement.nextElement;
                    count++;
                    if (index==count) return thisElement;
                }
            }
        }
        return null;
    }


    @Override
    public int getSize() {
        if (isNull()) return 0;
        else{
            return this.size;
        }
    }

    @Override
    public boolean isNull() {
        return this.head==null;
    }

    @Override
    public int getIndex(T element) {
        int index = 0;
        MyListElement<T> thisElement = this.head;
        while(thisElement!=null){
            if (thisElement.element.equals(element)) {
                return index;
            }
            thisElement = thisElement.nextElement;
            index++;
        }
        return -2;
    }

    @Override
    public boolean contains(T element) {
        MyListElement<T> thisElement = this.head;
        while(thisElement!=null){
            if (thisElement.element.equals(element)) return true;
            thisElement = thisElement.nextElement;
        }
        return false;
    }

    public void merge(MyList<T> toMergeList){
        if (isNull()) {
            head = toMergeList.head;
            size=toMergeList.size;
        }
        else if(!toMergeList.isNull()){
            MyListElement<T> thisElement = toMergeList.head;
            while(thisElement!=null) {
                T elementToAdd = thisElement.element;
                if (!contains(elementToAdd)) add(elementToAdd);
                thisElement = thisElement.nextElement;
            }
        }
    }

    @Override
    public String toString() {
        String output = "";
        if (isNull()) return "Empty List";
        else{
            for (int i = 0; i <getSize() ; i++) {
                output = output + "/" + get(i);
            }
            output = output + "/";
        }
        return output;
    }
}
interface MyCollection<T> {
    public void add(T newElement);
    public int remove(int index);
    public MyListElement<T> get(int index);
    public int getSize();
    public boolean isNull();
    public int getIndex(T element);
    public boolean contains(T element);
}
class Algorithm {
    private MyList<String> topics;
    private int depth;
    private SocialMedia socialMedia;
    private MyList<Boolean> chosenList;
    public Algorithm(MyList<String> topics, int depth, SocialMedia socialMedia) {
        this.topics = topics;
        this.depth = depth;
        this.socialMedia = socialMedia;
        this.chosenList = new MyList<>();
    }

    public void run(){
        SparseMatrix directMatrix = makeDirectList();
        if (!directMatrix.isNull()) makeIndirectList(directMatrix);
    }


    public void makeIndirectList(SparseMatrix directMatrix){
        SparseMatrix powerMatrix=new SparseMatrix(), decisionMatrix;

        for (int i = 0; i <depth ; i++) {
            PhoneBook book = new PhoneBook();
            switch (i){
                case 0:
                    powerMatrix = this.socialMedia.getFavoritePersons();
                    break;
                case 1:
                    powerMatrix = this.socialMedia.getFavoritePersons2();
                    break;
                case 2:
                    powerMatrix = this.socialMedia.getFavoritePersons3();
                    break;
            }

            decisionMatrix = SparseMatrix.multiply2(powerMatrix,directMatrix);

            int chosenPerson;
            double value;
            for (int j = 0; j <decisionMatrix.getSize() ; j++) {
                chosenPerson = decisionMatrix.get(j).row;
                value = decisionMatrix.get(j).value;
                if (!chosenList.get(chosenPerson).element){
                    book.add(this.socialMedia.getPersons().get(chosenPerson).element,chosenPerson,value);
                    chosenList.get(chosenPerson).element = true;
                }
            }
            printOutput(book,i);
        }
    }

    public void printOutput(PhoneBook outputBook, int stage){
        String depthString = "";
        for (int i = 0; i < stage+1; i++) {
            depthString = depthString + "+";
        }
        PhoneBookElement thisElement = outputBook.head;
        while (true){
            if (thisElement==null) break;
            else{
                System.out.print(thisElement.name + " ");
                System.out.printf("%.6f "+ depthString + "\n",thisElement.value);
                thisElement = thisElement.nextItem;
            }
        }
    }

    public SparseMatrix makeDirectList() {
        PhoneBook book = new PhoneBook();
        SparseMatrix thisRow;
        double totalValue;
        int numOfMatches;
        MyList<Integer> topicNumbers = new MyList<>();
        SparseMatrixElement thisElement;
        SparseMatrix directMatrix = new SparseMatrix();
        int totallTopicNumber = topics.getSize();
        for (int i = 0; i < topics.getSize(); i++) {
            topicNumbers.add(socialMedia.getTopics().getIndex(String.valueOf(topics.get(i))));
        }
        for (int i = 0; i < this.socialMedia.getPersons().getSize() ; i++) {
            thisRow = this.socialMedia.getFavoriteTopics().getRow(i);
            totalValue = 0;
            numOfMatches = 0;
            for (int j = 0; j < thisRow.getSize() ; j++) {
                thisElement = thisRow.get(j);
                if (topicNumbers.contains(thisElement.column)){
                    numOfMatches++;
                    totalValue = totalValue + thisElement.value;
                }
            }
            if (numOfMatches==totallTopicNumber) {
                book.add(this.socialMedia.getPersons().get(i).element,i,totalValue);
                directMatrix.addElement(i,0,totalValue);
                chosenList.add(true);
            } else chosenList.add(false);
        }
        printOutput(book,-1);
        return directMatrix;
    }
}

class PhoneBook {
    public PhoneBookElement head;
    public void add(String name, int id, double value){
        PhoneBookElement newElement = new PhoneBookElement(name,id,value);
        if (head==null) head = newElement;
        else {
            PhoneBookElement thisElement = head;
            PhoneBookElement previousElement = null;
            boolean done = false;
            while (thisElement.nextItem != null) {
                if (thisElement.compare(newElement)== PhoneBookElement.Posistion.BEFORE){
                    if (previousElement==null){
                        newElement.nextItem=thisElement;
                        head = newElement;
                        done = true;
                        break;
                    }else{
                        previousElement.nextItem = newElement;
                        newElement.nextItem = thisElement;
                        done = true;
                        break;
                    }
                } else {
                    previousElement = thisElement;
                    thisElement = thisElement.nextItem;
                }
            }
            if (!done){
                if (thisElement.compare(newElement)== PhoneBookElement.Posistion.BEFORE){
                    if (previousElement==null){
                        newElement.nextItem = thisElement;
                        head = newElement;
                    } else {
                        previousElement.nextItem = newElement;
                        newElement.nextItem = thisElement;
                    }
                } else thisElement.nextItem=newElement;
            }
        }
    }

    @Override
    public String toString() {
        if (head==null) return "Null";
        else return head.toString();
    }
}
class PhoneBookElement{
    public String name;
    public int id;
    public double value;
    public PhoneBookElement nextItem;

    public PhoneBookElement(String name, int id, double value) {
        this.name = name;
        this.id = id;
        this.value = value;
        this.nextItem = null;
    }

    public Posistion compare(PhoneBookElement element){
        if (element.value<this.value) return Posistion.AFTER;
        else if(element.value>this.value) return Posistion.BEFORE;
        else{
            if (element.name.compareTo(this.name)>0) return Posistion.AFTER;
            else return Posistion.BEFORE;
        }
    }

    @Override
    public String toString() {
        return name + "," + id + "," + value + ">" + nextItem;
    }

    enum Posistion{
        AFTER,
        BEFORE;
    }
}

public class Main {

    public static void main(String[] args) {
        Parser parser = new Parser();
        MyList<SocialMedia> socialMediaList = parser.parseInput();
        SocialMedia socialMedia = SocialMedia.mergeAll(socialMediaList);
        parser.parseCommands(socialMedia);

    }
}