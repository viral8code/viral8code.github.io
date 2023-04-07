class AVL:
	class Node:
		def __init__(self,parent,value):
			self._value = value;
			self._parent = parent;
			self._height = 1
			self._size = 1
			self._right = None
			self._left = None

	def __init__(self,li=[]):
		self._root = None
		self._size = 0
		self._hash = 0
		for num in li:
			self.add(num)

	def add(self,x):
		bool = True
		if self._root is None:
			self._root = self.Node(None,x)
		else:
			par = None
			now = self._root
			while True:
				par = now
				if x<now._value:
					now = now._left
				elif x>now._value:
					now = now._right
				else:
					bool = False
					break
				if now is None:
					break
			if bool:
				if x<par._value:
					par._left = self.Node(par,x)
				else:
					par._right = self.Node(par,x)
				self.__fix(par)
		if bool:
			self._size += 1
			self._hash ^= hash(x)
		return bool

	def __getitem__(self, index):
		if self._root is None or self._size<=index:
			return None
		now = self._root
		while True:
			ls = now._left._size if now._left is not None else 0
			if index<ls:
				now = now._left
			elif ls<index:
				now = now._right
				index -= ls + 1
			else:
				break
		return now._value

	def getindex(self,value):
		ans = 0;
		now = self._root
		while now is not None:
			if value<now._value:
				now = now._left
			elif value>now._value:
				ans += now._left._size+1 if now._left is not None else 1
				now = now._right
			else:
				ans += now._left._size if now._left is not None else 0
				return ans
		return ~ans;

	def remove(self,x):
		n = self.__getnode(x)
		if n is None:
			return False
		self._size -= 1
		self._hash ^= hash(x)
		self.__delete(n)
		return True

	def __delete(self,node):
		if node is not None:
			if node._left is None and node._right is None:
				if node._parent is not None:
					if node._parent._left is node:
						node._parent._left = None
					else:
						node._parent._right = None
					self.__fix(node._parent)
				else:
					self._root = None
				del node
			else:
				if node._left is not None and node._right is not None:
					rep = self.__getfirstnode(node._right)
					node._value = rep._value
					self.__delete(rep)
				else:
					rep = node._left if node._left is not None else node._right
					rep._parent = node._parent
					if node._parent is not None:
						if node._parent._left is node:
							node._parent._left = rep
						else:
							node._parent._right = rep
						self.__fix(node._parent)
					else:
						self._root = rep
					node._parent = None

	def __getnode(self,x):
		now = self._root;
		while now is not None:
			if x<now._value:
				now = now._left
			elif x>now._value:
				now = now._right
			else:
				break
		return now

	def first(self):
		if self._root is None:
			return None
		return self.__getfirstnode(self._root)._value

	def __getfirstnode(self,node):
		par = None
		while node is not None:
			par = node
			node = par._left
		return par

	def last(self):
		if self._root is None:
			return None
		return self.__getlastnode(self._root)._value

	def __getlastnode(self,node):
		par = None
		while node is not None:
			par = node
			node = par._right
		return par

	def __contains__(self,x):
		if self._root is None:
			return false
		return __getnode(x) is not None

	def poll_first(self):
		if self._root is None:
			return None
		self._size -= 1
		mini = self.__getfirstnode(self._root)
		ans = mini._value
		self.__delete(mini)
		self._hash ^= hash(ans)
		return ans

	def poll_last(self):
		if self._root is None:
			return None
		self._size -= 1
		maxi = self.__getlastnode(self._root)
		ans = maxi._value
		self.__delete(maxi)
		self._hash ^= hash(ans)
		return ans

	def ceiling(self,x):
		if self._root is None:
			return None
		return self.__ceiling(self._root,x)

	def __ceiling(self,node,x):
		ans = None
		while node is not None:
			if x>node._value:
				node = node._right
			elif x<node._value:
				ans = node
				node = node._left
			else:
				return x
		return ans._value if ans is not None else None

	def higher(self,x):
		if self._root is None:
			return None
		return self.__higher(self._root,x)

	def __higher(self,node,x):
		ans = None
		while node is not None:
			if x>=node._value:
				node = node._right
			else:
				ans = node
				node = node._left
		return ans._value if ans is not None else None

	def floor(self,x):
		if self._root is None:
			return None
		return __floor(self._root,x)

	def __floor(self,node,x):
		ans = None
		while node is not None:
			if x<node._value:
				node = node._left
			elif x>node._value:
				ans = node
				node = node._right
			else:
				return x
		return ans._value if ans is not None else None

	def lower(self,x):
		if self._root is None:
			return None
		return self.__lower(self._root,x)

	def __lower(self,node,x):
		ans = None
		while node is not None:
			if x<=node._value:
				node = node._left
			else:
				ans = node
				node = node._right
		return ans._value if ans is not None else None

	def clear(self):
		self._root = None
		self._size = 0
		self._hash = 0

	def is_empty(self):
		return self._size==0

	def tolist(self):
		li = []
		if self._root is not None:
			deq = []
			deq.append(self._root)
			while len(deq):
				now = deq.pop()
				if len(li)==0:
					if now._left is not None:
						deq.append(now)
						deq.append(now._left)
					else:
						li.append(now._value)
						if now._right is not None:
							deq.append(now._right)
				elif now._left is not None and li[-1]<now._left._value:
					deq.append(now)
					deq.append(now._left)
				else:
					li.append(now._value)
					if now._right is not None:
						deq.append(now._right)
		return li

	def __fix(self,node):
		while node is not None:
			lh = 0 if node._left is None else node._left._height
			rh = 0 if node._right is None else node._right._height
			if lh-rh>1:
				if node._left._right is not None and node._left._right._height==lh-1:
					self.__rotateleft(node._left)
				self.__rotateright( node )
			elif rh-lh>1:
				if node._right._left is not None and node._right._left._height==rh-1:
					self.__rotateright(node._right)
				self.__rotateleft(node)
			else:
				self.__setstates(node)
			node = node._parent

	def __rotateright(self,node):
		temp = node._left
		node._left = temp._right
		if node._left is not None:
			node._left._parent = node
		temp._right = node
		temp._parent = node._parent
		if node._parent is not None:
			if node._parent._left is node:
				node._parent._left = temp
			else:
				node._parent._right = temp
		else:
			self._root = temp
		node._parent = temp
		self.__setstates(node)

	def __rotateleft(self,node):
		temp = node._right
		node._right = temp._left
		if node._right is not None:
			node._right._parent = node
		temp._left = node
		temp._parent = node._parent
		if node._parent is not None:
			if node._parent._left is node:
				node._parent._left = temp
			else:
				node._parent._right = temp
		else:
			self._root = temp
		node._parent = temp
		self.__setstates(node)

	def __setstates(self,node):
		lh = 0 if node._left is None else node._left._height
		rh = 0 if node._right is None else node._right._height
		node._height = max(lh,rh)+1
		ls = 0 if node._left is None else node._left._size
		rs = 0 if node._right is None else node._right._size
		node._size = ls+rs+1

	def __len__(self):
		return self._size

	def __del__(self):
		del self

	def __str__(self):
		return str(self.tolist())

	def __hash__(self):
		return self._hash

	def __and__(self,other):
		li1 = self.tolist()
		li2 = other.tolist()
		index1 = 0
		index2 = 0
		ans = AVL()
		while index1<len(li1) and index2<len(li2):
			if li1[index1]==li2[index2]:
				ans.add(li1[index1])
				index1 += 1
				index2 += 1
			elif li1[index1]<li2[index2]:
				index1 += 1
			else:
				index2 += 1
		return ans

	def __or__(self,other):
		li1 = self.tolist()
		li2 = other.tolist()
		index1 = 0
		index2 = 0
		ans = AVL()
		for num in li1:
			ans.add(num)
		for num in li2:
			ans.add(num)
		return ans

	def __xor__(self,other):
		li1 = self.tolist()
		li2 = other.tolist()
		index1 = 0
		index2 = 0
		ans = AVL()
		while index1<len(li1) and index2<len(li2):
			if li1[index1]==li2[index2]:
				index1 += 1
				index2 += 1
			elif li1[index1]<li2[index2]:
				ans.add(li1[index1])
				index1 += 1
			else:
				ans.add(li1[index2])
				index2 += 1
		return ans

	def __iadd__(self, other):
		li = other.tolist()
		for num in li:
			self.add(num)
		return self

	def __isub__(self, other):
		li = other.tolist()
		for num in li:
			self.remove(num)
		return self

	def __iand__(self, other):
		li1 = self.tolist()
		li2 = other.tolist()
		index1 = 0
		index2 = 0
		while index1<len(li1) and index2<len(li2):
			if li1[index1]==li2[index2]:
				index1 += 1
				index2 += 1
			elif li1[index1]<li2[index2]:
				self.remove(li1[index1])
				index1 += 1
			else:
				index2 += 1
		return self

	def __or__(self,other):
		li = other.tolist()
		for num in li:
			self.add(num)
		return self

	def __xor__(self,other):
		li1 = self.tolist()
		li2 = other.tolist()
		index1 = 0
		index2 = 0
		while index1<len(li1) and index2<len(li2):
			if li1[index1]==li2[index2]:
				self.remove(li1[index1])
				index1 += 1
				index2 += 1
			elif li1[index1]<li2[index2]:
				index1 += 1
			else:
				self.add(li1[index2])
				index2 += 1
		return self

	def __eq__(self, other):
		if self is other:
			return True
		if not isinstance(other,AVL):
			return False
		if len(self)!=len(other):
			return False
		if hash(self)!=hash(other):
			return False
		li1 = self.tolist()
		li2 = other.tolist()
		for a,b in zip(li1,li2):
			if a!=b:
				return False
		return True

	def __ne__(self, other):
		return not self==other

	def __setitem__(self, key, value):
		if self.remove(key):
			self.add(value)

	def __delitem__(self, key):
		self.remove(key)

	def __iter__(self):
		return iter(self.tolist())
