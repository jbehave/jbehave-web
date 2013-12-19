package org.jbehave.web.runner.wicket.tree;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.wicket.MetaDataKey;
import org.apache.wicket.Session;

@SuppressWarnings("serial")
public class TreeResourceSet implements Set<TreeResource>,
		Serializable {

	private static MetaDataKey<TreeResourceSet> KEY = new MetaDataKey<TreeResourceSet>(){};

	private Set<String> names = new HashSet<String>();

	private boolean inverse;

	public void expandTree() {
		names.clear();
		inverse = true;
	}

	public void collapseTree() {
		names.clear();
		inverse = false;
	}

	public boolean add(TreeResource resource) {
		if (inverse) {
			return names.remove(resource.getName());
		} else {
			return names.add(resource.getName());
		}
	}

	public boolean remove(Object o) {
		TreeResource resource = (TreeResource) o;
		if (inverse) {
			return names.add(resource.getName());
		} else {
			return names.remove(resource.getName());
		}
	}

	public boolean contains(Object o) {
		TreeResource resource = (TreeResource) o;
		if (inverse) {
			return !names.contains(resource.getName());
		} else {
			return names.contains(resource.getName());
		}
	}

	public void clear() {
		throw new UnsupportedOperationException();
	}

	public int size() {
		throw new UnsupportedOperationException();
	}

	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	public <A> A[] toArray(A[] a) {
		throw new UnsupportedOperationException();
	}

	public Iterator<TreeResource> iterator() {
		throw new UnsupportedOperationException();
	}

	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	public boolean addAll(Collection<? extends TreeResource> c) {
		throw new UnsupportedOperationException();
	}

	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	public static TreeResourceSet get() {
		TreeResourceSet set = Session.get().getMetaData(KEY);
		if (set == null) {
			set = new TreeResourceSet();
			Session.get().setMetaData(KEY, set);
		}
		return set;
	}
}
