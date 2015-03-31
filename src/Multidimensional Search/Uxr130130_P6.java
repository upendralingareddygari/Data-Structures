import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * 
 * @author upendra
 *
 */
class Store {
	TreeMap<Long, Item> treeWithId;
	HashMap<Long, TreeMap<Double, Integer>> nameMap;

	/**
	 * Constructor for the supporting class
	 */
	Store() {
		treeWithId = new TreeMap<Long, Item>();
		nameMap = new HashMap<Long, TreeMap<Double, Integer>>();
	}

	/**
	 * insert functionality which maintains tree map with ids
	 * @param id id of the product
	 * @param price price of the product
	 * @param names of the product
	 * @return value 1 for new element else 0 for an old element updation
	 */
	public int insert(Long id, Double price, ArrayList<Long> names) {
		if (treeWithId.containsKey(id)) {
			Double oldPrice = treeWithId.get(id).getPrice();
			ArrayList<Long> oldItemNames = treeWithId.get(id).getNames();
			treeWithId.remove(id);
			
			if (oldItemNames != null) {				
				for (Long oldName : oldItemNames) {
					Integer count = nameMap.get(oldName).get(oldPrice);
					nameMap.get(oldName).remove(oldPrice);
					
					if(count - 1 == 0) {
						if(nameMap.get(oldName).size() == 0){
							nameMap.remove(oldName);
						}
					}else {
						nameMap.get(oldName).put(oldPrice, count - 1);	
					}
				}
				
				if(names == null) {
					names = oldItemNames;					
				}
				treeWithId.put(id, new Item(id, price, names));
				for (Long newName : names) {
					if (nameMap.containsKey(newName)) {
						if (nameMap.get(newName).containsKey(price)) {
							Integer count = nameMap.get(newName).get(price);
							nameMap.get(newName).remove(price);
							nameMap.get(newName).put(price, count + 1);
						} else {
							nameMap.get(newName).put(price, 1);
						}
					} else {
						TreeMap<Double, Integer> temp = new TreeMap<Double, Integer>();
						temp.put(price, 1);
						nameMap.put(newName, temp);
					}					
				}
			} else {
				if(nameMap.containsKey(null)) {
					Integer count = nameMap.get(null).get(oldPrice);
					nameMap.get(null).remove(oldPrice);
					
					if(count - 1 == 0) {
						if(nameMap.get(null).size() == 0){
							nameMap.remove(null);
						}
					}else {
						nameMap.get(null).put(oldPrice, count - 1);	
					}
				}
				
				if(names == null) {
					if (nameMap.containsKey(null)) {
						if (nameMap.get(null).containsKey(price)) {
							int c = nameMap.get(null).get(price);
							nameMap.get(null).remove(price);
							nameMap.get(null).put(price, c + 1);
						} else {
							nameMap.get(null).put(price, 1);
						}
					} else {
						TreeMap<Double, Integer> temp = new TreeMap<Double, Integer>();
						temp.put(price, 1);
						nameMap.put(null, temp);
					}
				} else {
					for (Long newName : names) {
						if (nameMap.containsKey(newName)) {
							if (nameMap.get(newName).containsKey(price)) {
								Integer count = nameMap.get(newName).get(price);
								nameMap.get(newName).remove(price);
								nameMap.get(newName).put(price, count + 1);
							} else {
								nameMap.get(newName).put(price, 1);
							}
						} else {
							TreeMap<Double, Integer> temp = new TreeMap<Double, Integer>();
							temp.put(price, 1);
							nameMap.put(newName, temp);
						}
					}
				}				
			}
			return 0;
		} else {
			insertNewNode(id, price, names);
			return 1;
		}
	}
	
	/**
	 * finding the price of a given product 
	 * @param id of the product
	 * @return price of the product
	 */
	public Double find(Long id) {
		if(!treeWithId.containsKey(id)) {
			return 0.0;
		} else {
			return treeWithId.get(id).getPrice();
		}
	}
	
	/**
	 * find min price in the given names
	 * @param n name of the product
	 * @return min price in the given name set
	 */
	public double findMinPrice(Long n) {
		if(nameMap.get(n) == null || nameMap.get(n).size() == 0) {
			return 0.0;
		} else {
			TreeMap<Double, Integer> map = nameMap.get(n);
			return map.firstKey();
		}
	}
	
	/**
	 * find max price in the give name set
	 * @param n name od the product
	 * @return max price in the given name set
	 */
	public double findMaxPrice(Long n) {
		if(nameMap.get(n) == null) {
			return 0.0;
		} else {
			return nameMap.get(n).lastKey();
		}
	}
	
	/**
	 * number of elements in the given name range
	 * @param n name set to look for the products
	 * @param low value
	 * @param high value
	 * @return number of items in the given range
	 */
	public int findPriceRange(Long n, Double low, Double high) {
		int result = 0;
		if(nameMap.get(n) == null) {
			return result;
		} else {
			TreeMap<Double, Integer> map = nameMap.get(n);
			for(Map.Entry<Double, Integer> entry : map.entrySet()) {
				if(low >= entry.getKey() && entry.getKey() <= high) {
					result += entry.getValue();
				}
			}
			return result;
		}
	}
	
	/**
	 * rise the price of the given id range
	 * @param start value of id to look for
	 * @param end value of id to look for
	 * @param rate by which the price needs to change
	 * @return summed up prices for the given id range
	 */
	public double PriceHike(Long start, Long end, Long rate) {
		double result = 0;
		for(Iterator<Map.Entry<Long, Item>> entry = treeWithId.entrySet().iterator(); entry.hasNext();) {
			Map.Entry<Long, Item> current = entry.next();
			Long currentValue = current.getKey();
			Item item = current.getValue();
			if(start <= currentValue && currentValue <= end) {
				Double oldPrice = item.getPrice();				
				//treeWithId.remove(item.getId());
				double temp = item.getPrice()*rate / 100;
				result = result + temp;
				item.setPrice(item.getPrice() + temp);
				current.setValue(item);
				if(item.getNames() == null) {
					Integer count = nameMap.get(null).get(oldPrice);
					nameMap.get(null).remove(oldPrice);
					if(count - 1 != 0) {
						nameMap.get(null).put(oldPrice, count - 1);
					}
					if(nameMap.get(null).get(item.getPrice()) == null) {
						nameMap.get(null).put(item.getPrice(), 1);
					} else {
						Integer count1 = nameMap.get(null).get(item.getPrice());
						nameMap.get(null).remove(item.getPrice());
						nameMap.get(null).put(item.getPrice(), count1 + 1);
					}
				} else {
					for(Long name:item.getNames()) {
						Integer count = nameMap.get(name).get(oldPrice);
						nameMap.get(name).remove(oldPrice);
						if(count - 1 != 0) {
							nameMap.get(name).put(oldPrice, count - 1);
						}
						if(nameMap.get(name).get(item.getPrice()) == null) {
							nameMap.get(name).put(item.getPrice(), 1);
						} else {
							Integer count1 = nameMap.get(name).get(item.getPrice());
							nameMap.get(name).remove(item.getPrice());
							nameMap.get(name).put(item.getPrice(), count1 + 1);
						}					
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * delete the given id
	 * @param id of the product
	 * @return sum of names for the given id
	 */
	public Long delete(Long id) {
		if(!treeWithId.containsKey(id)) {
			return (long)0;
		} else {
			Long result = (long)0;
			Item item = treeWithId.get(id);
			if(item.getNames() == null) {
				Double price = item.getPrice();
				Integer count = nameMap.get(null).get(price);
				nameMap.get(null).remove(price);
				if(count - 1 == 0) {
					if(nameMap.get(null) == null){
						nameMap.remove(null);
					}
				}else {
					nameMap.get(null).put(price, count - 1);	
				}
			} else {
				for(Long name:item.getNames()) {
					result += name;
					TreeMap<Double,Integer> price = nameMap.get(name);
					if(price.containsKey(item.getPrice())){
						price.remove(item.getPrice());
					}
					
				}
			}
			treeWithId.remove(id);
			return result;
		}
	}
	
	private void insertNewNode(Long id, Double price, ArrayList<Long> names) {
		Item item = new Item(id, price, names);
		treeWithId.put(id, item);
		if (names == null) {
			if (nameMap.containsKey(null)) {
				if (nameMap.get(null).containsKey(price)) {
					Integer count = nameMap.get(null).get(price);
					nameMap.get(null).remove(price);
					nameMap.get(null).put(price, count + 1);
					
				} else {
					nameMap.get(null).put(price, 1);
				}
			} else {
				TreeMap<Double, Integer> temp = new TreeMap<Double, Integer>();
				temp.put(price, 1);
				nameMap.put(null, temp);
			}
		} else {
			for (Long name : names) {
				if (nameMap.containsKey(name)) {
					if (nameMap.get(name).containsKey(price)) {
						Integer count = nameMap.get(name).get(price);
						nameMap.get(name).remove(price);
						nameMap.get(name).put(price, count + 1);
					} else {
						nameMap.get(name).put(price, 1);
					}
				} else {
					TreeMap<Double, Integer> temp = new TreeMap<Double, Integer>();
					temp.put(price, 1);
					nameMap.put(name, temp);
				}
			}
		}

	}

}

/**
 * 
 * @author upendra
 *
 */
class Item {
	private Long id;
	private Double price;
	private ArrayList<Long> names;

	Item(Long id, Double price, ArrayList<Long> names) {
		this.id = id;
		this.price = price;
		this.names = names;
	}

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public ArrayList<Long> getNames() {
		return names;
	}

	public void setNames(ArrayList<Long> names) {
		this.names = names;
	}
}

/**
 * 
 * @author upendra
 *
 */
public class Uxr130130_P6 {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(System.in);
		double result = 0;
		Store store = new Store();
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			line = line.replaceAll("\\s+", " ");
			//System.out.println(line);
			String[] tokens = line.split(" ");
			if (tokens[0].equals("Insert")) {
				Long id = Long.parseLong(tokens[1]);
				Double price = Double.parseDouble(tokens[2]);
				ArrayList<Long> names = null;
				if (!tokens[3].equals("0")) {
					int index = 3;
					names = new ArrayList<Long>();
					while (!tokens[index].equals("0")) {
						names.add(new Long(tokens[index]));
						index++;
					}
				}
				result += store.insert(id, price, names);
			} else if (tokens[0].equals("Find")) {
				Long id = Long.parseLong(tokens[1]);
				result += store.find(id);
			} else if (tokens[0].equals("Delete")) {
				Long id = Long.parseLong(tokens[1]);
				result += store.delete(id);
			} else if (tokens[0].equals("FindMinPrice")) {
				Long name = Long.parseLong(tokens[1]);
				result += store.findMinPrice(name);
			} else if (tokens[0].equals("FindMaxPrice")) {
				Long name = Long.parseLong(tokens[1]);
				result += store.findMaxPrice(name);
			} else if (tokens[0].equals("FindPriceRange")) {
				Long name = Long.parseLong(tokens[1]);
				double low = Double.parseDouble(tokens[2]);
				double high = Double.parseDouble(tokens[3]);
				result += store.findPriceRange(name, low, high);
			} else if (tokens[0].equals("PriceHike")){
				// Price hike
				Long low = Long.parseLong(tokens[1]);
				Long high = Long.parseLong(tokens[2]);
				Long rate = Long.parseLong(tokens[3]);
				result += store.PriceHike(low, high, rate);
			}
		}
		scanner.close();
		System.out.println(result);
	}
}
