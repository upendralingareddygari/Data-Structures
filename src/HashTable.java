import java.io.File;
import java.util.Scanner;

/*
 * 
 * 	L Upendra Reddy
 * 	java version "1.7.0_25"
	Java(TM) SE Runtime Environment (build 1.7.0_25-b17)
	Java HotSpot(TM) 64-Bit Server VM (build 23.25-b01, mixed mode)
 */

class Item {
	private String _key;
	private String _record;

	public Item(String key, String record) {
		_key = key;
		_record = record;
	}

	public String getKey() {
		return _key;
	}

	public String getRecord() {
		return _record;
	}
}

class MyHashTable {
	private static final int maxSize = 1013;
	private float noOfPrbsInser = 0;
	private float noOfPrbsInNoSuccess = 0;
	private float noOfPrbsInSuccess = 0;
	private float noOfInsertions = 0;
	private float noOfSuccSearchs = 0;
	private float noOfNoSuccSearchs = 0;
	private Item[] hashTable = null;
	private int tableSize = 0;
	private int noOfKeys = 0;

	public MyHashTable()
	{
		this(maxSize);
	}

	public MyHashTable(int value)
	{
		hashTable = new Item[value];
		tableSize = value;
	}

	public void DoubleHash(int value)
	{
		hashTable = new Item[value];
		tableSize = value;
	}

	private int hashFunctionOne(String value)
	{
		int result = 0;

		for(int i =0; i < value.length(); i++)
		{
			result = 37*result + value.charAt(i);
		}

		result %= tableSize;

		if(result < 0)
		{
			result += tableSize;
		}

		return result;
	}

	private int hashFunctionTwo(int value)
	{
		return 13-(value%13);
	}

	public int size()
	{
		return noOfKeys;
	}

	public int capacity()
	{
		return tableSize;
	}

	public void listAll()
	{
		if(noOfKeys != 0)
		{
			for(int i = 0; i < tableSize; i++)
			{
				if(hashTable[i] != null && !hashTable[i].getKey().equals(""))
				{
					System.out.println(i + " " + hashTable[i].getKey() + " " + hashTable[i].getRecord());
				}
			}
		}
	}

	public String isPresent(String key)
	{
		String result = null;
		int noOfProbes = 0;
		if(noOfKeys != 0)
		{
			int position = hashFunctionOne(key);
			int offSet = hashFunctionTwo(position);
			int lookupCount = 0;
			while(hashTable[(position + lookupCount*offSet)%tableSize] != null && lookupCount != tableSize)
			{	
				noOfProbes++;
				if(hashTable[(position + lookupCount*offSet)%tableSize].getKey().equals(key))
				{
					result = hashTable[(position + lookupCount*offSet)%tableSize].getRecord();					
					break;
				}
				lookupCount++;
			}
		}

		if(noOfProbes == 0)
		{
			noOfProbes++;
		}

		if(result == null)
		{
			noOfPrbsInNoSuccess += noOfProbes;
			noOfNoSuccSearchs++;
		}
		else
		{
			noOfPrbsInSuccess += noOfProbes;
			noOfSuccSearchs++;
		}
		return result;
	}

	public boolean delete(String key)
	{
		boolean result = false;
		if(noOfKeys != 0)
		{
			int position = hashFunctionOne(key);
			int offSet = hashFunctionTwo(position);
			int lookupCount = 0;
			while(hashTable[(position + lookupCount*offSet)%tableSize] != null && lookupCount != tableSize)
			{				
				if(hashTable[(position + lookupCount*offSet)%tableSize].getKey().equals(key))
				{
					hashTable[(position + lookupCount*offSet)%tableSize] = new Item("", "");
					noOfKeys--;					
					result = true;
					break;
				}
				lookupCount++;
			}
		}
		return result;
	}

	public boolean insert(String key, String record)
	{
		int location = getLocation(key, record);
		if(location == -1)
		{
			return false;
		}
		else
		{
			hashTable[location] = new Item(key, record);
			noOfKeys++;
			noOfInsertions++;
			return true;
		}
	}

	public void printStats()
	{
		Float avgPrbsInsrt;
		Float avgPrbsInSuces;
		Float avgPrbsInNSuces;

		if(noOfInsertions > 0)
		{
			//System.out.println(noOfPrbsInser + " " + noOfInsertions);
			avgPrbsInsrt = noOfPrbsInser/noOfInsertions;
			System.out.printf("Average probes in insert  = %.1f\n", avgPrbsInsrt);
		}
		else
		{
			System.out.println("Zero insertions");
		}

		if(noOfSuccSearchs > 0)
		{
			avgPrbsInSuces = noOfPrbsInSuccess/noOfSuccSearchs;
			System.out.printf("Average probes in successful search = %.1f\n", avgPrbsInSuces);
		}
		else
		{
			System.out.println("Zero successful searches");
		}

		if(noOfNoSuccSearchs>0)
		{
			avgPrbsInNSuces = noOfPrbsInNoSuccess/noOfNoSuccSearchs;
			System.out.printf("Average probes in unsuccessful search = %.1f\n", avgPrbsInNSuces);
		}
		else
		{
			System.out.println("zero unsuccessful searches");
		}
	}

	private int getLocation(String key, String record)
	{
		int result = -1;

		int position = hashFunctionOne(key);
		int offSet = hashFunctionTwo(position);
		int lookUpCount = 0;
		int firstDummyLocation = -1;
		int noOfPrbs = 0;

		while(hashTable[(position + lookUpCount*offSet)%tableSize] != null && lookUpCount != tableSize)
		{
			noOfPrbs++;
			if(hashTable[(position + lookUpCount*offSet)%tableSize].getKey().equals(key))
			{
				break;
			}
			else if(hashTable[(position + lookUpCount*offSet)%tableSize].getKey().equals(""))
			{
				if(firstDummyLocation == -1)
				{
					firstDummyLocation = (position + lookUpCount*offSet)%tableSize;
				}				
			}
			lookUpCount++;
		}

		if(noOfPrbs == 0)
		{
			noOfPrbs++;
		}

		noOfPrbsInser += noOfPrbs;

		if(hashTable[(position + lookUpCount*offSet)%tableSize] == null)
		{
			result = firstDummyLocation != -1 ? firstDummyLocation: (position + lookUpCount*offSet)%tableSize;
		}
		else
		{
			if(lookUpCount == tableSize && firstDummyLocation != -1)
			{
				result = firstDummyLocation;
			}
		}		

		return result;
	}

	public void rehash()
	{
		tableSize = getNextPrime(tableSize);
		Item[] hashTable1 = hashTable;
		hashTable = new Item[tableSize];
		noOfKeys = 0;
		for(int i = 0; i < hashTable1.length; i++)
		{
			if(hashTable1[i] != null && !hashTable1[i].getKey().equals(""))
			{
				insert(hashTable1[i].getKey(), hashTable1[i].getRecord());
			}
		}
	}

	private int getNextPrime(int number)
	{
		number++;
		while(!isPrime(number))
		{
			number++;
		}
		return number;		
	}

	private boolean isPrime(int number)
	{
		boolean result = false;

		for(int i = 2; i*i <= number; i++)
		{
			if(number%i==0)
			{
				result = true;
			}
		}

		return result;
	}
}

public class UPREP6 
{

	public static void main(String[] args) throws Exception 
	{
		//Scanner input = new Scanner(new File("C:\\Users\\uppi\\Desktop\\h1.txt"));
		Scanner input = new Scanner(System.in);
		MyHashTable hashTable = null;
		while(input.hasNextLine())
		{
			String option = input.nextLine();
			if(option.length() == 1)
			{
				switch(option)
				{
				case "N":
					System.out.println("Lingareddygari Upendra Reddy");
					break;

				case "C":
					//C clear the hash table to empty and reset the statistics
					if(hashTable != null)
					{
						hashTable = new MyHashTable();
					}
					break;

				case "Z":
					if(hashTable != null)
					{
						System.out.println(hashTable.size());
					}
					else
					{
						System.out.println("0");
					}
					//Z	// Print the line "Size is n" where n is the number of records stored in the table
					break;

				case "P":
					hashTable.listAll();
					//P	// Print a list of the elements in the Table in the order of their position in the table,
					// one record per line in the form "# key:Record"
					break;

				case "T":
					hashTable.printStats();
					//T	// Print the following three lines
					// Average probes in insert = #.#
					// Average probes in unsuccessful search = #.#
					// Average probes in successful search = #.#
					// where the values are rounded to one decimal place				
					break;

				case "E":
					input.close();
					System.exit(0);
					break;

				default:
					System.out.println();
					break;
				}
			}
			else
			{
				if(option.startsWith("I"))
				{				
					if(hashTable == null)
					{
						hashTable = new MyHashTable();
					}

					if(hashTable.capacity() == hashTable.size())
					{
						System.out.println("Table has overflowed");
					}
					else
					{
						String key = option.substring(2, option.indexOf(':'));
						String record = option.substring(option.indexOf(':')+1);
						boolean result = hashTable.insert(key, record);
						if(result)
						{
							if(hashTable.size() > hashTable.capacity()/2)
							{
								hashTable.rehash();
							}						

							System.out.println("Key " + key + " inserted");
						}
						else
						{
							System.out.println("Key soap already exists");
						}
					}
					//I: soap:Keeps you clean // Insert record "Keeps you clean" with "soap" as its key
					// Print one of the lines "Key soap inserted", "Key soap already exists", OR
					// "Table has overflowed". In the last case, the record isn't inserted

				}
				else if(option.startsWith("D"))
				{	
					String key = option.substring(2);
					if(hashTable == null || hashTable.size() == 0)
					{
						System.out.println("hashtable is empty");
					}
					else
					{
						boolean result = hashTable.delete(key);
						if(result)
						{
							System.out.println("Key " + key +" deleted");
						}
						else
						{
							System.out.println("Key " + key +" not present");
						}
					}
					//D tin	// Delete the record that has "tin" as a key
					// Print one of the lines "Key tin deleted" OR "Key tin not present"
				}
				else
				{
					String key = option.substring(2);
					if(hashTable == null || hashTable.size() == 0)
					{
						System.out.println("Hashtable is empty");
					}
					else
					{
						String result = hashTable.isPresent(key);
						if(result != null)
						{
							System.out.println("Key " + key +":" + result);
						}
						else
						{
							System.out.println("Key " + key +" not found");
						}					
					}
					//S fort	// Search for the key "fort"
					// Print one of the lines "Key fort:" then the record corresponding to that key, OR
					// "Key fort not found"			
				}
			}
		}
	}

}
