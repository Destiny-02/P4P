package payroll;
import java.util.List;
import java.util.Vector;
public abstract class PayrollList<E> {
	final protected static int capacity = 999;
	final protected static int increment = 999;
	protected int _length;
	protected List<E> _list;
	public List<E> getList(){
		return _list;
	}
	public void initialise (){
		_list= new Vector <E>(capacity, increment);
	}
	public void add(E e){
		_list.add(e);
		_length++;
	}
	public int getLength(){
		return _length;
	}
	public boolean isEmpty(){
		if (_length == 0){
			return true;
		}
		else{
			return false;
		}
	}
}
