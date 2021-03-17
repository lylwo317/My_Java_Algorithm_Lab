
## 思路一:
从左开始找第一个逆序对，为起始点start。从右开始找第一个逆序对为终点end。
对[start, end]排序即可。
这个思路存在一个致命误区。start,end排序后是有序了。
但是你没发保证排序后的a[start] >= a[0...start-1]
也没法保证a[end] <= a[end+1...a.length-1]。
一个返例就是如下:
    1,2,4,7,10,11,7,12,6,7,16,18,19.

这样找则start=5, end=8.

## 思路二:
我们上述思路之无效是因为，我们没法保证[start,end]排完序后是
a[start] >= a[0...start-1] && a[end] <= a[end+1...a.length-1]。

思路二取巧的地方就在于编码，想都很好想。
我们的确遍历的时候无法做到 发现一个序列是逆序对，还要保证遍历过得逆序对的最小值大于前面。
如果我们可以做到，那我们要如何记录这个start，是记录第一个逆序对的位置吗？如思路一，肯定不可行。

所以我们反其道而行之，我们设置一个最大值。用来标注每一段逆序序列的最大值。这样我们就能找到每段连续的逆序
序列的最右短点。而最右端点的下一个位置必然是大于最大值的, 如果不是那么就不是最右断点,
所以如果最右断点一直递增到结尾，则我们只需要对[0,最右断点排序]。同时又保证了a[end] <= a[end+1...a.length-1]。

同理反过来。
我们设置最小值，从右往左扫，也能找到一个最左端点。使得 a[start] >= a[0...start-1]

那么此时的[start, end]就是最小的排序序列了.
- 如果还有比a[start]小的，意味着其就不是最左断点，我们用这个断点更新start。所以最终start肯定就是最左的最小。
- 同理end可以这样论证。

```class 
class Solution {
        public int[] subSort(int[] array) {

            if (array.length == 0) return new int[]{-1,-1};

            int start = 0;
            int max = array[0];
            
            int r = -1;
            for (int i = 1; i < array.length; i++) {

                if (array[i] >= max) {
                    max = array[i];
                }else {
                    r = i;
                }
            }

            int l = -1;
            int min = array[array.length - 1];
            for (int i =  array.length-1; i >= 0; i--) {

                if (array[i] <= min) {
                    min = array[i];
                }else {
                    l = i;
                }
            }

            return new int[]{l, r};

        }
    }
```