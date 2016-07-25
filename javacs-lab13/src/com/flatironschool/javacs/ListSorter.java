/**
 *
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 *
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {

		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 *
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 *
	 * Returns a list that might be new.
	 *
	 * @param list
	 * @param comparator
	 * @return
	 */
	 public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
		 List<T> rlist = new ArrayList<T>(list);
		 List<T> left = null;
		 List<T> right = null;

 		if (rlist.size() <= 100) {
 			insertionSort(rlist, comparator);
 		} else {
 			left = rlist.subList(0, rlist.size()/2);
 			right = rlist.subList(rlist.size()/2, rlist.size());

 			left = mergeSort(left, comparator);
 			right = mergeSort(right, comparator);
			merge(left, right, rlist, comparator);
 		}

		return rlist;
 	}

	private void merge(List<T> left, List<T> right, List<T> original,
									   Comparator<T> comparator) {
		int lefti = 0;
		int righti = 0;
		int originali = 0;

		while (lefti < left.size() && righti < right.size()) {
			if (comparator.compare(left.get(lefti), right.get(righti)) < 0) {
				original.set(originali, left.get(lefti));
				lefti++;
			} else {
				original.set(originali, right.get(righti));
				righti++;
			}
			originali++;
		}

		List<T> rest;
		int resti;
		if (lefti >= left.size()) {
			rest = right;
			resti = righti;
		} else {
			rest = left;
			resti = lefti;
		}

		for (int i = resti; i < rest.size(); i++) {
        original.set(originali, rest.get(i));
        originali++;
    }
	}

	/**
	 * Sorts a list using a Comparator object.
	 *
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
		 PriorityQueue<T> pq = new PriorityQueue<T>(list.size(), comparator);
		 for(T elem : list) {
			 pq.offer(elem);
		 }
		 for(int i = 0; i < list.size(); i++) {
			 list.set(i, pq.poll());
		 }
	}


	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 *
	 * @param k
	 * @param list
	 * @param comparator
	 * @return
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
		List<T> rlist = new ArrayList<T>(k);
		PriorityQueue<T> pq = new PriorityQueue<T>(list.size(), comparator);
		for(T elem : list) {
			if (pq.size() < k) {
				pq.offer(elem);
			} else if (comparator.compare(pq.peek(), elem) < 0) {
				pq.poll();
				pq.offer(elem);
			}
		}
		for(int i = 0; i < k; i++) {
			rlist.add(pq.poll());
		}
		return rlist;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));

		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};

		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
